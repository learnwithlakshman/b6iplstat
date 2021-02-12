package com.careerit.ipl.service;

import java.util.List;

import com.careerit.ipl.dto.LabelDTO;
import com.careerit.ipl.dto.PlayerDTO;
import com.careerit.ipl.dto.RoleCountDTO;

public interface IplStatService {

	public LabelDTO getLabels();

	public List<PlayerDTO> getPlayersBy(String label);

	public List<RoleCountDTO> getRoleCountBy(String label);

	public List<PlayerDTO> getPlayersByRoleAndLabel(String roleName, String label);
}
