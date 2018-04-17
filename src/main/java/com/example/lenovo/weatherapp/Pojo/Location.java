package com.example.lenovo.weatherapp.Pojo;

public class Location
{
    private String region;

    private String localtime;

    private String localtime_epoch;

    private String lon;

    private String tz_id;

    private String name;

    private String lat;

    private String country;

    public String getRegion ()
    {
        return region;
    }

    public void setRegion (String region)
    {
        this.region = region;
    }

    public String getLocaltime ()
    {
        return localtime;
    }

    public void setLocaltime (String localtime)
    {
        this.localtime = localtime;
    }

    public String getLocaltime_epoch ()
    {
        return localtime_epoch;
    }

    public void setLocaltime_epoch (String localtime_epoch)
    {
        this.localtime_epoch = localtime_epoch;
    }

    public String getLon ()
    {
        return lon;
    }

    public void setLon (String lon)
    {
        this.lon = lon;
    }

    public String getTz_id ()
    {
        return tz_id;
    }

    public void setTz_id (String tz_id)
    {
        this.tz_id = tz_id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getLat ()
    {
        return lat;
    }

    public void setLat (String lat)
    {
        this.lat = lat;
    }

    public String getCountry ()
    {
        return country;
    }

    public void setCountry (String country)
    {
        this.country = country;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [region = "+region+", localtime = "+localtime+", localtime_epoch = "+localtime_epoch+", lon = "+lon+", tz_id = "+tz_id+", name = "+name+", lat = "+lat+", country = "+country+"]";
    }
}