package com.rahul.udacity.cs2.ui.home;

import com.rahul.udacity.cs2.model.HomeModel;

import java.util.ArrayList;

/**
 * Created by rahulgupta on 21/01/17.
 */
class HomePresenter {

    HomePresenter() {

    }

    ArrayList<HomeModel> createList() {
        ArrayList<HomeModel> placesArrayList = new ArrayList<>();
        placesArrayList.add(new HomeModel("New Delhi, India", "ChIJLbZ-NFv9DDkRzk0gTkm3wlI"));
        placesArrayList.add(new HomeModel("Bangalore, Karnataka, India", "ChIJbU60yXAWrjsR4E9-UejD3_g"));
        placesArrayList.add(new HomeModel("Gujarat, India", "ChIJlfcOXx8FWTkRLlJU7YfYG4Y"));
        placesArrayList.add(new HomeModel("Himachal Pradesh, India", "ChIJ9wH5Z8NTBDkRJXdLVsUE_nw"));
        placesArrayList.add(new HomeModel("Chennai, Tamil Nadu, India", "ChIJYTN9T-plUjoRM9RjaAunYW4"));
        placesArrayList.add(new HomeModel("Kerala, India", "ChIJW_Wc1P8SCDsRmXw47fuQvWQ"));
        placesArrayList.add(new HomeModel("Mumbai, Maharashtra, India", "ChIJwe1EZjDG5zsRaYxkjY_tpF0"));
        placesArrayList.add(new HomeModel("Goa, India", "ChIJQbc2YxC6vzsRkkDzYv-H-Oo"));
        placesArrayList.add(new HomeModel("Jammu & Kashmir", "ChIJnaj_mSQJ4TgR8eeXRm16VgY"));
        placesArrayList.add(new HomeModel("New York, NY, United States", "ChIJOwg_06VPwokRYv534QaPC8g"));
        placesArrayList.add(new HomeModel("Sydney, New South Wales, Australia", "ChIJP3Sa8ziYEmsRUKgyFmh9AQM"));

        return placesArrayList;
    }
}
