package com.careerit.ipl.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.careerit.ipl.dto.LabelDTO;
import com.careerit.ipl.dto.PlayerDTO;
import com.careerit.ipl.dto.RoleCountDTO;

@Repository
public class IplStatDaoImpl implements IplStatDao {

	private static final Logger log = LoggerFactory.getLogger(IplStatDaoImpl.class);
	private final MongoOperations mongoOperations;

	public IplStatDaoImpl(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	@Override
	public LabelDTO findLabels() {
		GroupOperation group = group("null").addToSet("label").as("labels");
		ProjectionOperation project = project().andExclude("_id");
		Aggregation aggr = newAggregation(group, project);
		AggregationResults<LabelDTO> res = mongoOperations.aggregate(aggr, "team", LabelDTO.class);
		LabelDTO obj = res.getUniqueMappedResult();
		log.info("Total labels found is {}", obj.getLabels().size());
		return obj;
	}

	@Override
	public List<PlayerDTO> findPlayersBy(String label) {
		MatchOperation match = match(Criteria.where("label").is(label));
		UnwindOperation unwind = unwind("players");
		ProjectionOperation project = project().and("players.name").as("name").and("players.price").as("price")
				.and("players.role").as("role").and("label").as("label").andExclude("_id");
		Aggregation aggr = newAggregation(match, unwind, project);
		AggregationResults<PlayerDTO> res = mongoOperations.aggregate(aggr, "team", PlayerDTO.class);
		List<PlayerDTO> list = res.getMappedResults();
		log.info("Total player found is : {} for team : {}", list.size());
		return list;
	}

	@Override
	public List<RoleCountDTO> findRoleCountBy(String label) {
		MatchOperation match = match(Criteria.where("label").is(label));
		UnwindOperation unwind = unwind("players");
		GroupOperation group = group("players.role").count().as("count");
		ProjectionOperation project = project().andExclude("_id").and("_id").as("roleName").andInclude("count");
		Aggregation aggr = newAggregation(match, unwind, group, project);
		AggregationResults<RoleCountDTO> res = mongoOperations.aggregate(aggr, "team", RoleCountDTO.class);
		List<RoleCountDTO> list = res.getMappedResults();
		log.info("Total roles {} found for the team: {}", list.size(), label);
		return list;

	}

	@Override
	public List<PlayerDTO> findPlayersByRoleAndLabel(String roleName, String label) {
		MatchOperation match1 = match(Criteria.where("label").is(label));
		UnwindOperation unwind = unwind("players");
		MatchOperation match2 = match(Criteria.where("players.role").is(roleName));
		ProjectionOperation project = project().and("players.name").as("name").and("players.price").as("price")
				.and("players.role").as("role").and("label").as("label").andExclude("_id");
		Aggregation aggr = newAggregation(match1, unwind, match2, project);
		AggregationResults<PlayerDTO> res = mongoOperations.aggregate(aggr, "team", PlayerDTO.class);
		List<PlayerDTO> list = res.getMappedResults();
		log.info("Total player found : {} for the  team : {} and role : {}", list.size(), label, roleName);
		return list;
	}

}
