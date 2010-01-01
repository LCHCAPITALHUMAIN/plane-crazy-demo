package com.google.model;


import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;



@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class FavoriteActivity {
	   @PrimaryKey
	    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	    private Long id;

	    @Persistent
	    private String username;


		@Persistent
	    private String GfcUsername;
		

		@Persistent
	    private String displayName;


		@Persistent
	    private String thumbnailUrl;


		@Persistent
	    private String activity;

		@Persistent
	    private Date date;

		@Persistent
	    private String itemId;




		public FavoriteActivity(String username, String GfcUsername, String displayName, String thumbnailUrl, String activity, String itemId, Date date ) {
		  this.username = username;
		  this.GfcUsername = GfcUsername;
		  this.displayName = displayName;
		  this.thumbnailUrl = thumbnailUrl;
		  this.activity = activity;
		  this.itemId = itemId;
		  this.date = date;
	    }

		public String getUsername() {
			return username;
		}



		public void setUsername(String username) {
			this.username = username;
		}




		public String getGfcUsername() {
			return GfcUsername;
		}




		public void setGfcUsername(String gfcUsername) {
			GfcUsername = gfcUsername;
		}




		public Date getDate() {
			return date;
		}



		public void setDate(Date date) {
			this.date = date;
		}


		

		public String getDisplayName() {
			return displayName;
		}



		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}



		

		public String getActivity() {
			return activity;
		}



		public void setActivity(String activity) {
			this.activity = activity;
		}
		
		
	    public Long getId() {
	        return id;
	    }
	    

		public String getThumbnailUrl() {
			return thumbnailUrl;
		}
		
		
		
		public void setThumbnailUrl(String thumbnailUrl) {
			this.thumbnailUrl = thumbnailUrl;
		}

		public String getItemId() {
			return itemId;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}
	   

 }
