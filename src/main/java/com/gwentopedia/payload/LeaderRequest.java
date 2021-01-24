package com.gwentopedia.payload;

import javax.validation.constraints.NotBlank;

public class LeaderRequest {
	@NotBlank
    private String name;
	@NotBlank
    private String provisions;
	@NotBlank
    private String ability;	
	@NotBlank
    private String imgurl;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProvisions() {
		return provisions;
	}
	public void setProvisions(String provisions) {
		this.provisions = provisions;
	}
	public String getAbility() {
		return ability;
	}
	public void setAbility(String ability) {
		this.ability = ability;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
}
