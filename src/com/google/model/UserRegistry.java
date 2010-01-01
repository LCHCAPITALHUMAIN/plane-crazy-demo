package com.google.model;

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class UserRegistry {
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Long id;

  @Persistent
  private String username;

  @Persistent
  private String gfcUsername;

  @Persistent
  private String password;

  @Persistent
  private String email;

  @Persistent
  private boolean postActivity;

  @Persistent
  private boolean sendEmail;

  @Persistent
  private String emailVerifyKey;


@Persistent
  private boolean emailVerified;

  

  @Persistent
  private String thumbnailUrl;


  @Persistent
  private String favePlane;


  @Persistent
  private Date date;

  public UserRegistry(String username, String gfcUsername,  String password, String email, boolean sendEmail, boolean emailVerified, boolean postActivity, String thumbnailUrl, String favePlane, Date date) {
    this.username = username;
    this.gfcUsername = gfcUsername;
    this.email = email;
    this.sendEmail = sendEmail;
    this.emailVerified = emailVerified;
    this.postActivity = postActivity;
    this.thumbnailUrl = thumbnailUrl;
    this.password = password;
    this.favePlane = favePlane;
    this.date = date;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }


  public String getPassword() {
    return password;
  }


  public String getGfcUsername() {
    return gfcUsername;
  }


  public Date getDate() {
    return date;
  }


  public String getThumbnailUrl() {
    return thumbnailUrl;
  }


  public String getFavePlane() {
    return favePlane;
  }

  public void setFavePlane(String favePlane) {
    this.favePlane = favePlane;
  }

  public void setEmail(String email) {
	this.email = email;
}

public String getEmail() {
	return email;
}

public void setPostActivity(boolean postActivity) {
	this.postActivity = postActivity;
}

public void setSendEmail(boolean sendEmail) {
	this.sendEmail = sendEmail;
}

public boolean isSendEmail() {
	return sendEmail;
}

public boolean isPostActivity() {
	return postActivity;
}

public String getEmailVerifyKey() {
	return emailVerifyKey;
}

public void setEmailVerifyKey(String emailVerifyKey) {
	this.emailVerifyKey = emailVerifyKey;
}

public boolean isEmailVerified() {
	return emailVerified;
}

public void setEmailVerified(boolean emailVerified) {
	this.emailVerified = emailVerified;
}



public void setThumbnailUrl(String thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setGfcUsername(String onlineUsername) {
    this.gfcUsername = onlineUsername;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
