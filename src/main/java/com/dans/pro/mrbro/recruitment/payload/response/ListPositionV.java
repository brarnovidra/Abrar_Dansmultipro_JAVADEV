package com.dans.pro.mrbro.recruitment.payload.response;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ListPositionV {

	@Getter
	@Setter
	@NoArgsConstructor
	public static class ListPositionResponseItem implements Serializable {
		@Expose
		@JsonProperty("company_logo")
		private String companyLogo;

		@Expose
		@JsonProperty("how_to_apply")
		private String howToApply;

		@Expose
		@JsonProperty("created_at")
		private String createdAt;

		@Expose
		@JsonProperty("description")
		private String description;

		@Expose
		@JsonProperty("company")
		private String company;

		@Expose
		@JsonProperty("company_url")
		private String companyUrl;

		@Expose
		@JsonProperty("location")
		private String location;

		@Expose
		@JsonProperty("id")
		private String id;

		@Expose
		@JsonProperty("type")
		private String type;

		@Expose
		@JsonProperty("title")
		private String title;

		@Expose
		@JsonProperty("url")
		private String url;

		public ListPositionResponseItem(String companyLogo, String howToApply, String createdAt, String description, String company, String companyUrl, String location, String id, String type, String title, String url) {
			this.companyLogo = companyLogo;
			this.howToApply = howToApply;
			this.createdAt = createdAt;
			this.description = description;
			this.company = company;
			this.companyUrl = companyUrl;
			this.location = location;
			this.id = id;
			this.type = type;
			this.title = title;
			this.url = url;
		}
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class ListPositionResponse implements Serializable {
		@Expose
		@JsonProperty("payload")
		private List<ListPositionResponseItem> listPositionResponse;

		public ListPositionResponse(List<ListPositionResponseItem> listPositionResponse) {
			this.listPositionResponse = listPositionResponse;
		}
	}


}
