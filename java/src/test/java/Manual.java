import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Workshop Manual")
class WorkshopManual {

    @Nested
    @DisplayName("01. Common Assertions")
    class CommonAssertions {

        @Test
        void equality() {
            int val = 42;
            assertEquals(42, val);
        }

        @Test
        void truth() {
            assertTrue(true);
            assertFalse(false);
        }

        @Test
        void exceptions() {
            assertThrows(Exception.class, () -> errorFunction());
        }

        void errorFunction() throws Exception {
            throw new Exception();
        }
    }

    @Nested
    @DisplayName("02. Test Doubles")
    class TestDoubles {

        class Dependent {
            void log(String message) throws InterruptedException {
                // A long operation we might want to shortcut.
                sleep(500000);
            }

            boolean isValid() {
                return false;
            }

            void saveName(WuTangName name) {
                // write to DB, etc.
            }
        }

        class Controller {
            private Dependent dependent;

            Controller(Dependent dependent) {

                this.dependent = dependent;
            }

            void conditionalPath() {


                if (this.dependent.isValid()) {
                    try {
                        this.dependent.log("Valid!");
                    } catch (Exception ignored) {
                    }
                } else {
                    try {
                        this.dependent.log("Not valid!");
                    } catch (Exception ignored) {
                    }
                }

            }

            void generateName(String personName) {
                if (personName.equals("Dave"))
                    this.dependent.saveName(new WuTangName("Smilin' Prophet"));
            }
        }

        final class WuTangName {

            private String wuTangName;

            public WuTangName(String value) {
                this.wuTangName = value;
            }

            public String getTheName() {
                return this.wuTangName;
            }
        }

        @Test
        void stubsAndMocks() {

            Dependent doubleDependent = mock(Dependent.class);
            when(doubleDependent.isValid()).thenReturn(false); // Stub this method

            Controller subject = new Controller(doubleDependent);

            subject.conditionalPath();

            try {
                verify(doubleDependent).log("Not valid!"); // This is a strict mock
                verify(doubleDependent).log(anyString()); // A loose mock (any string)
            } catch (Exception ignored) { }

        }

        @Test
        void argumentCapture() {

            Dependent doubleDependent = mock(Dependent.class);
            ArgumentCaptor<WuTangName> captor = ArgumentCaptor.forClass(WuTangName.class);

            Controller subject = new Controller(doubleDependent);

            subject.generateName("Dave"); // Notice we're passing in a regular string

            verify(doubleDependent).saveName(captor.capture());

            String theCreatedName = captor.getValue().getTheName();

            assertEquals("Smilin' Prophet", theCreatedName);
        }
    }

}
