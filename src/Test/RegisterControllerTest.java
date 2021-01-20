package Test;

import GUI.controllers.RegisterController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegisterControllerTest {

    RegisterController registerController;

    @BeforeEach
    void setUp() {
        registerController = new RegisterController();
    }

    @Test
    public void isTheSameString_WhenStringsAreTheSame_ReturnTrue(){
        Boolean result = registerController.isTheSameString("theSame","theSame");

        Assertions.assertTrue(result);
    }

    @Test
    public void isTheSameString_WhenStringsAreDifferent_ReturnFalse(){
        Boolean result = registerController.isTheSameString("Aaaaa","Bbbbb");

        Assertions.assertFalse(result);
    }

    @Test
    public void isUsernameHasMin8Letters_WhenUsernameHas9Letters_ReturnTrue(){
        Boolean result = registerController.isUsernameHasMin8Letters("asdfghjkl");

        Assertions.assertTrue(result);
    }

    @Test
    public void isUsernameHasMin8Letters_WhenUsernameHas8Letters_ReturnTrue(){
        Boolean result = registerController.isUsernameHasMin8Letters("asdfghjk");

        Assertions.assertTrue(result);
    }

    @Test
    public void isUsernameHasMin8Letters_WhenUsernameHas7Letters_ReturnFalse(){
        Boolean result = registerController.isUsernameHasMin8Letters("asdfghj");

        Assertions.assertFalse(result);
    }
}