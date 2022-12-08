package engine;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;



class FileManagerTest {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    void setUpOutputStream() {
        System.setOut(new PrintStream(output));
    }
    @AfterEach
    void resotreOutputStream() {
        System.setOut(System.out);
        output.reset();
    }

    void setInputValues(String input) {
    }


    @Test
    @Tag("loadCoins")
    void loadCoins() throws IOException {
        input_saveCoins();
        assertEquals(3000, FileManager.getInstance().loadCoins());
    }

    @Test
    @Tag("input_saveCoins")
    void input_saveCoins() {
        setInputValues("3000");
        FileManager.getInstance().saveCoins(3000);
    }

    @Test
    @Tag("output_saveCoins")
    void output_saveCoins() {
        String compareResult= "3000";
        FileManager.getInstance().saveCoins(3000);
        assertThat(output.toString().trim(),is(equalTo(compareResult)));
    }

    @Test
    void loadCashItem(){
    }

    @Test
    void input_saveCashItem(){
    }

    @Test
    void output_saveCashItem(){
    }


}