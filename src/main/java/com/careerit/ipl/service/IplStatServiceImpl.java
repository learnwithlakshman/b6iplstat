package com.careerit.ipl.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.careerit.ipl.dao.IplStatDao;
import com.careerit.ipl.dto.LabelDTO;
import com.careerit.ipl.dto.PlayerDTO;
import com.careerit.ipl.dto.RoleCountDTO;

@Service
public class IplStatServiceImpl implements IplStatService {

	private static final Logger log = LoggerFactory.getLogger(IplStatServiceImpl.class);
	private final IplStatDao iplStatDao;

	public IplStatServiceImpl(IplStatDao iplStatDao) {
		this.iplStatDao = iplStatDao;
	}

	@Override
	public LabelDTO getLabels() {
		LabelDTO labelDTO = iplStatDao.findLabels();
		log.info("Labels data :{}", labelDTO);
		return labelDTO;
	}

	@Override
	public List<PlayerDTO> getPlayersBy(String label) {
		return iplStatDao.findPlayersBy(label);
	}

	@Override
	public List<RoleCountDTO> getRoleCountBy(String label) {
		Assert.notNull(label, "Team label can not be empty or null");
		List<RoleCountDTO> list = iplStatDao.findRoleCountBy(label);
		log.info("Total roles retured is :{}", list.size());
		return list;
	}

	@Override
	public List<PlayerDTO> getPlayersByRoleAndLabel(String roleName, String label) {
		Assert.notNull(label, "Team label can not be empty or null");
		Assert.notNull(roleName, "Rolename can not be empty or null");
		List<PlayerDTO> list = iplStatDao.findPlayersByRoleAndLabel(roleName, label);
		log.info("Total players retured is :{}", list.size());
		return list;
	}

}
