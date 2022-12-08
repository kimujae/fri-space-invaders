package engine;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

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
    void input_saveCoins() {
    }

    @Test
    void output_saveCoins() {
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