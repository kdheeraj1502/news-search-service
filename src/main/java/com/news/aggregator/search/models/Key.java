package com.news.aggregator.search.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Key {
    private LocalDate localDate;
    private String query;
    public Key(String query, LocalDate localDate){
        this.query = query;
        this.localDate = localDate;
    }

    @Override
    public boolean equals(Object o) {
        Key key = (Key) o;
        return Objects.equals(localDate, key.localDate) && Objects.equals(query, key.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localDate, query);
    }
}
