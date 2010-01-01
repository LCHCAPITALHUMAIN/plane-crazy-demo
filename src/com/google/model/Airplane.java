package com.google.model;


import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.datastore.Text;


@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Airplane {
	   @PrimaryKey
	    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	    private Long id;

	    @Persistent
	    private String catalogId;

		@Persistent
	    private String name;

		@Persistent
	    private String manufacturer;


		@Persistent
	    private String thumbnailUrl;


		@Persistent
	    private String imgUrl;

		@Persistent
	    private String description;

		@Persistent
	    private Text longDescription;



		public Airplane(String guitarname, String manufacturer, String catalogId, String thumbnailUrl, String imgUrl, String description, Text longDescription) {
		       
	        this.name = guitarname;
	        this.manufacturer = manufacturer;
	        this.catalogId = catalogId;
	        this.thumbnailUrl = thumbnailUrl;
	        this.imgUrl = imgUrl;
	        this.description = description;	
	        this.longDescription = longDescription;
	    }

		
		
	    public String getImgUrl() {
			return imgUrl;
		}

		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getManufacturer() {
			return manufacturer;
		}

		public void setManufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
		}


		public void setName(String name) {
			this.name = name;
		}

		
		public Text getLongDescription() {
			return longDescription;
		}



		public void setLongDescription(Text longDescription) {
			this.longDescription = longDescription;
		}

		
		
	    public Long getId() {
	        return id;
	    }
	    
		public String getName() {
			return name;
		}

		public String getThumbnailUrl() {
			return thumbnailUrl;
		}
		
	    public String getCatalogId() {
			return catalogId;
		}

		public void setCatalogId(String catalogId) {
			this.catalogId = catalogId;
		}
		
		
		public void setThumbnailUrl(String thumbnailUrl) {
			this.thumbnailUrl = thumbnailUrl;
		}
	   

 }
