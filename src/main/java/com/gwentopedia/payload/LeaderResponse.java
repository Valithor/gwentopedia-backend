package com.gwentopedia.payload;

public class LeaderResponse {
	private long id;
    private String name;
    private String provisions;
    private String ability;	
    private String imgurl;
	public LeaderResponse(long id, String name, String provisions, String ability, String imgurl) {
		super();
		this.id = id;
		this.name = name;
		this.provisions = provisions;
		this.ability = ability;
		this.imgurl = imgurl;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
