package com.numerical.numerical.Utility;

//http://52.66.241.87/numerons/collection/58f66888d8be26ec238ce698
public class Const {
    public interface BASEURL {
     String URL = "https://numerical.co.in/apis/";
     //String URL = "http://52.66.241.87/apis/";
    }
    public interface ENDPOINT {
        String latest="numerons/latest/";
        String Mostviewed="numerons/mostviewed/";
        String Topics="topics";
        String LOGIN_URL="auth/getUser";
        String Social_LOGIN_URL="auth/signup";
        String Signup="auth/signup";
        String ByTopics="numerons/bytopic/";
        String ByCategoty="numerons/bycategory/";
        String ByPublisher="numerons/bypublisher/";
        String LikeNumerouns="numerons/";
        String FollowCollections="follow/";
        String Comment="numerons/";
        String Search="numerons/search/";


    }
}