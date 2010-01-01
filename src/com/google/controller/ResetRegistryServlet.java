package com.google.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.Text;
import com.google.model.Airplane;
import com.google.model.FavoriteActivity;
import com.google.model.PMF;
import com.google.model.UserRegistry;


public class ResetRegistryServlet extends HttpServlet {
  private static final Logger log = Logger.getLogger(ResetRegistryServlet.class.getName());

  private List<UserRegistry> userlist;
  private List<Airplane> airplanelist;
  private List<FavoriteActivity> faveactivitylist;

  public void doGet(HttpServletRequest req, HttpServletResponse resp)
  throws IOException {

    resp.setContentType("text/plain");
    resp.getWriter().println("Hello, GAE");
    cleanDatabase();
    System.out.println("database cleaned!");

    populateAllUsers();
    populateAirplaneList();
    populateFaveActivityList();
    System.out.println("redirecting to /app/admin/datadump.jsp");
    resp.sendRedirect("/app/admin/datadump.jsp");
  }


  public void populateAllUsers(){
    System.out.println("populating all users!");
    
    Date date = new Date();

    userlist = new ArrayList<UserRegistry>();

    userlist.add(new UserRegistry("stestington", "120951719733060", "pwd1", "stestington@foo.com", true, false, false, "http://chow-down.appspot.com/static/profilephotos/anon01.gif", "abc2", date));
    userlist.add(new UserRegistry("csaulk", "03612430409300", "pwd2", "cscaulk@foo.com", false, false, false, "http://chow-down.appspot.com/static/profilephotos/anon02.gif",  "abc2", date));
    userlist.add(new UserRegistry("jasonaco", "0174066039134600", "pwd3", "jasonaco@foo.com", false, false, false, "http://chow-down.appspot.com/static/profilephotos/anon03.gif", "abc2", date));
    userlist.add(new UserRegistry("fredsa", "13968561337675547", "pwd4", "fredsa@foo.com", false, false, false, "http://chow-down.appspot.com/static/profilephotos/anon04.gif", "abc1", date));
    userlist.add(new UserRegistry("patezon", "17397337280463984", "pwd5", "patezon@foo.com", false, false, false, "http://chow-down.appspot.com/static/profilephotos/anon03.gif", "abc3", date));
    userlist.add(new UserRegistry("njohnso", "0806328525286226", "pwd6", "njohnso@foo.com", false, false, false, "http://chow-down.appspot.com/static/profilephotos/anon02.gif", "abc4", date));
    userlist.add(new UserRegistry("cessnut", "343323233234", "pwd7",  "cessnut@foo.com", false, false, false, "http://chow-down.appspot.com/static/profilephotos/anon01.gif", "abc2", date));
    userlist.add(new UserRegistry("peterpiper", "343255333", "welcome", "peter@foo.com", false, false, false, "http://chow-down.appspot.com/static/profilephotos/anon02.gif", "abc2", date));

    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      for (int i=0; i<userlist.size(); i++) {
        pm.makePersistent((UserRegistry)userlist.get(i));
      } 
    } finally {
      pm.close();
    }
  }	


  public void populateAirplaneList(){
    System.out.println("populating airplane list");
    airplanelist = new ArrayList<Airplane>();


    airplanelist.add(new Airplane("Cessna 152", "Cessna", "abc1", "/images/cessna-152-sm.jpg", "/images/cessna-152.jpg", "The Cessna 152 is the world's most popular training aircraft.",

        new Text("First delivered in 1977 as the 1978 model year, the 152 was a modernization of the proven Cessna 150 design. The 152 was intended to compete with the new Beechcraft Skipper and Piper Tomahawk, both of which were introduced the same year." + 
        "Additional design goals were to improve useful load through a gross weight increase to 1670 lbs (757 kg), decrease internal and external noise levels and run better on the then newly introduced 100LL fuel")));

    airplanelist.add(new Airplane("Cessna 172", "Cessna", "abc2", "/images/cessna-172-sm.jpg", "/images/cessna-172.jpg", "With 4 seats the Cessna 172 is the world's most popular general aviation aircraft as well as trainer.", 
        new Text("Measured by its longevity and popularity, the Cessna 172 is the most successful mass produced light aircraft in history. The first production models were delivered in 1956 and they are still in production. As of 2008, more than 43,000 had been built." + 
        "The Skyhawk's main competitors have been the Beechcraft Musketeer and Grumman AA-5 series (neither in production), the Piper Cherokee and, more recently, the Diamond DA40.")));

    airplanelist.add(new Airplane("Cessna 172SP", "Cessna", "abc3", "/images/cessna-172sp-sm.jpg", "/images/cessna-172sp.jpg", "With its modern instruments, the 172SP is a sleek update to the well-known 172 plane.", 
        new Text("With more than 43,000 delivered, the Cessna Skyhawk is the best-selling, most-flown airplane ever. Maybe that's because of its proven reliability. Or its forgiving flight characteristics. Or its reputation as the safest general aviation aircraft ever built." + 
            "Because not only is the Skyhawk the most direct route to fulfilling your dreams of flight, it's also an ideal instrument trainer and one of the world's truly great airplanes. And with 180 horsepower attached to a high-performance McCauley propeller with polished " + 
        " chrome spinner, the Skyhawk SP climbs faster and cruises at a greater speed than the original Skyhawk.")));

    airplanelist.add(new Airplane("Cessna Skylane", "Cessna", "abc4", "/images/cessna-skylane-sm.jpg", "/images/cessna-skylane.jpg", "The Cessna Skylane serves as a powerful family transportation vehicle with over 200 horse power.", 
        new Text("The Cessna 182 was introduced in 1956 as a tricycle gear variant of the 180. In 1957, the 182A variant was introduced along with the name Skylane. Later models have added more powerful engines and bigger windows.")));

    airplanelist.add(new Airplane("Cirrus SR-20", "Cirrus", "abc5", "/images/cirrus-sr20-sm.jpg", "/images/cirrus-sr20.jpg", "The modern Cirrus SR-20 provides the latest in modern technology for personal aviation.", 
        new Text("The SR20 was certified by the FAA on 23 October 1998.[1] Hundreds of SR20s have been sold since the first was delivered in 1999. As of December 2006 over 2000 Cirrus aircraft had been delivered." + 
        "One of the major selling points for the SR20 is that it has a fully digital avionics suite with one 10-inch Avidyne FlightMax primary flight display and one multi-function display. A pair of Garmin GNS430s provide GPS navigation, conventional radio navigation, and radio communications.")));

    airplanelist.add(new Airplane("Cirrus SR-22", "Cirrus", "abc6", "/images/cirrus-sr22-sm.jpg", "/images/cirrus-sr22.jpg", "The more powerful ultra modern Cirrus SR-22 provides the latest in modern technology for personal aviation.", 
        new Text("he SR22, by Cirrus Design, is a high-performance single-engine, four-seat, composite aircraft. It is a more powerful version of the Cirrus SR20, with a larger wing, higher fuel capacity, and a 310 horsepower (231 kW) engine. " + 
        "It is extremely popular among purchasers of new aircraft and has been the world's best-selling single-engine, four-seat aircraft for several years.[2] Like the Cessna 400, but unlike most other high-performance aircraft, the SR22 has fixed (non-retractable) landing gear.")));

    airplanelist.add(new Airplane("Piper Warrior", "Piper", "abc7", "/images/piper-warrior-sm.jpg", "/images/piper-warrior.jpg", "The Piper Warrior is a well established general aviation workhorse.", 
        new Text("The Piper PA-28 Cherokee is a family of light aircraft designed for flight training, air taxi and personal use, built by Piper Aircraft. All members of the PA-28 family are all-metal, unpressurized, four-seat, single-engine piston-powered airplanes with" + 
        " low-mounted wings and tricycle landing gear. All PA-28 aircraft have a single door on the co-pilot side, which is entered by stepping on the wing. ")));


    airplanelist.add(new Airplane("Piper Archer", "Piper", "abc8", "/images/piper-archer-sm.jpg", "/images/piper-archer.jpg", "The Piper Archer is a more powerful cousin to the Piper Warrior.", 
        new Text("In 1962, Piper added the Cherokee 180 (PA-28-180) powered by a 180 horsepower (134 kW) Lycoming O-360 engine. The extra power made it practical to fly with all four seats filled (depending on passenger weight and fuel loading), and the model remains popular on the used-airplane market. " + 
        "In 1968, the cockpit was modified to replace the push-pull style engine controls with levers. In addition, a third window was added to each side, giving the fuselage the more modern look seen in current production. The current Archer model is the descendant of the Cherokee 180.")));

    airplanelist.add(new Airplane("Diamond DA-20", "Diamond", "abc9", "/images/diamond-da20-sm.jpg", "/images/diamond-da20.jpg", "The Diamond DA20 is a two-seat tricycle gear general aviation aircraft designed for flight training.", 
        new Text("The first DA20 was the Rotax 912 powered A1 Katana produced in Canada in 1994. It was the first Diamond aircraft available for sale in North America. Production of the Continental IO-240-B3B powered C1 Evolution and Eclipse models began in 1998, also in Canada. " + 
        "Production of the A1 Katana is complete but the DA20-C1 is still being constructed in 2007.")));


    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      for (int i=0; i<airplanelist.size(); i++) {
        System.out.println("adding an airplane");
        pm.makePersistent((Airplane)airplanelist.get(i));
      } 
    } finally {
      pm.close();
    }
  }	



  public void populateFaveActivityList(){
    System.out.println("populating fave activity list");
    faveactivitylist = new ArrayList<FavoriteActivity>();
    faveactivitylist.add(new FavoriteActivity("userid1", "cschalk", "Chris Schalk", "http://www.google.com/friendconnect/profile/picture/mHkt1jTdNjFkfCyWX3ATpk3adCmhAICl_klyq8A0zbkx5GUg0kPkDhNMtVmxvNgXkPu-ixLyyWUgiQD5xewPO-BTW58dL5mFDXkeXdNQPKA", "Chris Schalk just selected a Cessna 152 as his favorite plane", "abc1", new Date()));
    faveactivitylist.add(new FavoriteActivity("userid1", "cschalk", "Chris Schalk", "http://www.google.com/friendconnect/profile/picture/mHkt1jTdNjFkfCyWX3ATpk3adCmhAICl_klyq8A0zbkx5GUg0kPkDhNMtVmxvNgXkPu-ixLyyWUgiQD5xewPO-BTW58dL5mFDXkeXdNQPKA", "I still like the Cessna 152 best!", "abc1" , new Date()));
    faveactivitylist.add(new FavoriteActivity("userid1", "cschalk", "Chris Schalk", "http://www.google.com/friendconnect/profile/picture/mHkt1jTdNjFkfCyWX3ATpk3adCmhAICl_klyq8A0zbkx5GUg0kPkDhNMtVmxvNgXkPu-ixLyyWUgiQD5xewPO-BTW58dL5mFDXkeXdNQPKA", "Chris Schalk just selected a Cessna 172 as his favorite plane", "abc2", new Date()));
    faveactivitylist.add(new FavoriteActivity("userid1", "cschalk", "Chris Schalk", "http://www.google.com/friendconnect/profile/picture/mHkt1jTdNjFkfCyWX3ATpk3adCmhAICl_klyq8A0zbkx5GUg0kPkDhNMtVmxvNgXkPu-ixLyyWUgiQD5xewPO-BTW58dL5mFDXkeXdNQPKA", "Actually I think I like the Cessna 172SP best!", "abc3" , new Date()));


    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      for (int i=0; i<faveactivitylist.size(); i++) {
        System.out.println("adding an activity");
        pm.makePersistent((FavoriteActivity)faveactivitylist.get(i));
      } 
    } finally {
      pm.close();
    }
  }	



  public void cleanDatabase(){
    log.entering(ResetRegistryServlet.class.getName(), "cleanDatabase");
    System.out.println("cleaning database");



    PersistenceManager pm = PMF.get().getPersistenceManager();

    String query = "select from " + UserRegistry.class.getName();
    List<UserRegistry> users = (List<UserRegistry>) pm.newQuery(query).execute();


    query = "select from " + Airplane.class.getName();
    List<Airplane> airplanes = (List<Airplane>) pm.newQuery(query).execute();


    query = "select from " + FavoriteActivity.class.getName();
    List<FavoriteActivity> activities = (List<FavoriteActivity>) pm.newQuery(query).execute();

    try {
      pm.deletePersistentAll(users);
      pm.deletePersistentAll(airplanes);
      pm.deletePersistentAll(activities);

    }

    finally {
      pm.close();
    }

    System.out.println("Cleaned database");
  }    

} 

