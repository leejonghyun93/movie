package main;

import viewer.MovieViewer;
import viewer.RatingViewer;
import viewer.UserViewer;

public class MovieMain {
    public static void main(String[] args) {
        UserViewer userViewer = new UserViewer();
        MovieViewer movieViewer = new MovieViewer();
        RatingViewer ratingViewer = new RatingViewer();

        userViewer.setMovieViewer(movieViewer);
        userViewer.setRatingViewer(ratingViewer);

        movieViewer.setRatingViewer(ratingViewer);

        ratingViewer.setUserViewer(userViewer);

        userViewer.showIndex();
    }
}
