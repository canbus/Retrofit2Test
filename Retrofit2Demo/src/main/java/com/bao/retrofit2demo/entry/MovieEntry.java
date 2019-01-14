package com.bao.retrofit2demo.entry;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieEntry {
    private int count;
    private int start;
    private int total;
    private String title;
    private List<Subject> subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    private class Subject{
        //@SerializedName("genres")
        @SerializedName(value = "kinds",alternate={"genres","genre"})
        private List<String> kind;
        private String title;
        private String year;
        private Rating rating;

        public List<String> getRenres() {
            return kind;
        }

        public void setRenres(List<String> renres) {
            this.kind = renres;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public Rating getRating() {
            return rating;
        }

        public void setRating(Rating rating) {
            this.rating = rating;
        }

        private class Rating {
            float max;
            float average;
            float stars;
            float min;

            public float getMax() {
                return max;
            }

            public void setMax(float max) {
                this.max = max;
            }

            public float getAverage() {
                return average;
            }

            public void setAverage(float average) {
                this.average = average;
            }

            public float getStars() {
                return stars;
            }

            public void setStars(float stars) {
                this.stars = stars;
            }

            public float getMin() {
                return min;
            }

            public void setMin(float min) {
                this.min = min;
            }
        }
    }
}
