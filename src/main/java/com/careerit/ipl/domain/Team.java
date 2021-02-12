package com.careerit.ipl.domain;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Team {
		@Id
		private String id;
		private String city;
		private String coach;
		private String home;
		private String label;
		private String name;
		private List<Player> players;
}
