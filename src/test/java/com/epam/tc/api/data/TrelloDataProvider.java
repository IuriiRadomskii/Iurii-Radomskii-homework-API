package com.epam.tc.api.data;

import com.epam.tc.api.entities.Board;
import com.epam.tc.api.entities.TrelloList;
import org.testng.annotations.DataProvider;

public class TrelloDataProvider {

    @DataProvider
    public static Object[][] boardData() {
        Board board = new Board();
        board.setName("FOO");
        return new Object[][] {
            {board}
        };
    }

    @DataProvider
    public static Object[][] listData() {
        TrelloList list = new TrelloList();
        list.setName("BAR");
        return new Object[][] {
            {list}
        };
    }
}
