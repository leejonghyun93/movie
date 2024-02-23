package model;

public class MovieDTO {
    private int id;
    private String title;
    private String summary;
    private int filmGrade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getFilmGrade() {
        return filmGrade;
    }

    public void setFilmGrade(int filmGrade) {
        this.filmGrade = filmGrade;
    }

    public boolean equals(Object o) {
        if (o instanceof MovieDTO) {
            MovieDTO m = (MovieDTO) o;
            return id == m.id;
        }

        return false;
    }

    public MovieDTO() {
        id = 0;
        title = new String();
        summary = new String();
        filmGrade = 0;
    }

    public MovieDTO(MovieDTO m) {
        id = m.id;
        title = new String(m.title);
        summary = new String(m.summary);
        filmGrade = m.filmGrade;
    }
}
