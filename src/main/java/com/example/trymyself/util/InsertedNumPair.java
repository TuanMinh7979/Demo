package com.example.trymyself.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsertedNumPair {
    private int topInsNum;
    private int bottomInsNum;

    public InsertedNumPair inTopInsNum() {
        this.topInsNum += 1;
        return this;
    }



    public InsertedNumPair inBottomInsNum() {
        this.bottomInsNum += 1;
        return this;
    }




}
