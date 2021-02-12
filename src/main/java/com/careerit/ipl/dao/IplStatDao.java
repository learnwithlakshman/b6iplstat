package com.careerit.ipl.dao;

import java.util.List;

import com.careerit.ipl.dto.LabelDTO;
import com.careerit.ipl.dto.PlayerDTO;
import com.careerit.ipl.dto.RoleCountDTO;

public interface IplStatDao {
	public LabelDTO findLabels();

	public List<PlayerDTO> findPlayersBy(String label);

	public List<RoleCountDTO> findRoleCountBy(String label);

	public List<PlayerDTO> findPlayersByRoleAndLabel(String roleName, String label);
}
