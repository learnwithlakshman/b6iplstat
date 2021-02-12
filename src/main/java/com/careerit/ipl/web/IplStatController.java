package com.careerit.ipl.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.careerit.ipl.dto.LabelDTO;
import com.careerit.ipl.dto.PlayerDTO;
import com.careerit.ipl.dto.RoleCountDTO;
import com.careerit.ipl.service.IplStatService;

@RestController
@RequestMapping("/api/ipl")
public class IplStatController {

	private final IplStatService iplStatService;

	public IplStatController(IplStatService iplStatService) {
		this.iplStatService = iplStatService;
	}

	@GetMapping("/labels")
	public LabelDTO getLabels() {
		return iplStatService.getLabels();
	}

	@GetMapping("/players/{label}")
	public List<PlayerDTO> getPlayers(@PathVariable("label") String label) {
		return iplStatService.getPlayersBy(label);
	}

	@GetMapping("/players/{label}/{role}")
	public List<PlayerDTO> getPlayersByRoleAndLabel(@PathVariable("label") String label,
			@PathVariable("role") String roleName) {
		return iplStatService.getPlayersByRoleAndLabel(roleName, label);
	}

	@GetMapping("/rolecount/{label}")
	public List<RoleCountDTO> getRoleCount(@PathVariable("label") String label) {
		return iplStatService.getRoleCountBy(label);
	}
}
