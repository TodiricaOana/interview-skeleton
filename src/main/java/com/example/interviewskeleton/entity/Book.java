package com.example.interviewskeleton.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private int year;
    private String seoName;
    private String productCode;

    // added spaces before and after dash, removed space before first parenthesis
    public void generateSeoName() {
        this.seoName = author + " - " + title + " (" + year + ")";
    }

    public void generateProductCode() {
        if (seoName == null) {
            productCode = null;
            return;
        }

        StringBuilder codeBuilder = new StringBuilder();
        for (char ch : seoName.toCharArray()) {
            // when the character is a space, a dot should be appended
            if (ch == '(' || ch == ')' || ch == '-' || ch == ' ') {
                codeBuilder.append('.');
            } else if ("AEIOUaeiou".contains(String.valueOf(ch))) {
                codeBuilder.append('1');
            } else if (Character.isAlphabetic(ch)) {
                codeBuilder.append('0');
            } else if (Character.isDigit(ch)){
                // I considered that if the character is a digit, then it should be appended as it is to the Product Code
                // From the test, it is not clear how other characters should be converted, so I considered that they are ignored
                codeBuilder.append(ch);
            }
        }
        this.productCode = codeBuilder.toString();
    }
}
