package com.higamasa.juniorkanazawa.entity;

import java.io.Serializable;

/**
 * Created by banjousyunsuke on 2016/11/29.
 */

public class QuizEntity implements Serializable {
        private int id;
        private String statement;
        private String title;
        private String first;
        private String second;
        private String third;
        private String fourth;
        private int answer;
      private String drawable;

        public int getId() {
                return id;
        }

        public String getTitle() {
                return title;
        }

        public String getStatement() {
                return statement;
        }

        public String getFirst() {
                return first;
        }

        public String getSecond() {
                return second;
        }

        public String getThird() {
                return third;
        }

        public String getFourth() {
                return fourth;
        }

        public int getAnswer() {
                return answer;
        }

      public String getDrawable() {return drawable;}

        public void setId(int id) {
                this.id = id;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public void setStatement(String statement) {
                this.statement = statement;
        }

        public void setFirst(String first) {
                this.first = first;
        }

        public void setSecond(String second) {
                this.second = second;
        }

        public void setThird(String third) {
                this.third = third;
        }

        public void setFourth(String fourth) { this.fourth = fourth;}

                public void setDrawable(String drawable){this.drawable =drawable;}
        public void setAnswer(int answer) {
                this.answer = answer;
        }
}
