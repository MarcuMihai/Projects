import static org.junit.Assert.*;
import org.junit.*;

    public class JUnitTest {

        private static Operations m;
        private static int nrTesteExecutate = 0;
        private static int nrTesteCuSucces = 0;

        public JUnitTest() {}

        @AfterClass
        public static void tearDownAfterClass() throws Exception {
            System.out.println("S-au executat " + nrTesteExecutate + " teste din care "+ nrTesteCuSucces + " au avut succes!");
        }

        @Before
        public void setUp() throws Exception {
            nrTesteExecutate++;
        }

        @Test
        public void testAddition() {
            String s1="2x^2+4x-7";
            Polinom pol1=new Polinom();
            pol1.toPolynomial(s1);
            String s2="5x^3+20x-2";
            Polinom pol2=new Polinom();
            pol2.toPolynomial(s2);
            Polinom rez=new Polinom();
            rez=m.addition(pol1,pol2);
            assertNotNull(rez.toString());
            assertEquals(rez.toString(),"5.0*x^3+2.0*x^2+24.0*x-9.0");
            nrTesteCuSucces++;
        }

        @Test
        public void testSubtraction() {
            String s1="2x^2+7";
            Polinom pol1=new Polinom();
            pol1.toPolynomial(s1);
            String s2="3x-2";
            Polinom pol2=new Polinom();
            pol2.toPolynomial(s2);
            Polinom rez=new Polinom();
            rez=m.subtraction(pol1,pol2);
            assertNotNull(rez.toString());
            assertEquals(rez.toString(),"2.0*x^2-3.0*x+9.0");
            nrTesteCuSucces++;
        }

        @Test
        public void testMultiplication() {
            String s1="2x^2+7";
            Polinom pol1=new Polinom();
            pol1.toPolynomial(s1);
            String s2="3x-2";
            Polinom pol2=new Polinom();
            pol2.toPolynomial(s2);
            Polinom rez=new Polinom();
            rez=m.multiplication(pol1,pol2);
            assertNotNull(rez.toString());
            assertEquals(rez.toString(),"6.0*x^3-4.0*x^2+21.0*x-14.0");
            nrTesteCuSucces++;
        }

        @Test
        public void testDivision() {
            String s1="2x";
            Polinom pol1=new Polinom();
            pol1.toPolynomial(s1);
            String s2="2";
            Polinom pol2=new Polinom();
            pol2.toPolynomial(s2);
            String rez=new String();
            rez=m.division(pol1,pol2);
            String r=rez.toString();
            assertNotNull(r);
            String[] r1=r.split(" ");
            assertEquals(r1[0],"1.0*x");
            nrTesteCuSucces++;
        }

        @Test
        public void testDerivative() {
            String s1="2x^2+7";
            Polinom pol1=new Polinom();
            pol1.toPolynomial(s1);
            String s2="3x-2";
            Polinom pol2=new Polinom();
            pol2.toPolynomial(s2);
            Polinom rez1=new Polinom();
            Polinom rez2=new Polinom();
            rez1=m.derivative(pol1);
            rez2=m.derivative(pol2);
            assertNotNull(rez1.toString());
            assertEquals(rez1.toString(),"4.0*x+0");
            assertNotNull(rez2.toString());
            assertEquals(rez2.toString(),"3.0+0");
            nrTesteCuSucces++;
        }

        @Test
        public void testIntegration() {
            String s1="2x+7";
            Polinom pol1=new Polinom();
            pol1.toPolynomial(s1);
            String s2="4x-2";
            Polinom pol2=new Polinom();
            pol2.toPolynomial(s2);
            Polinom rez1=new Polinom();
            Polinom rez2=new Polinom();
            rez1=m.integration(pol1);
            rez2=m.integration(pol2);
            assertNotNull(rez1.toString());
            assertEquals(rez1.toString(),"1.0*x^2+7.0*x");
            assertNotNull(rez2.toString());
            assertEquals(rez2.toString(),"2.0*x^2-2.0*x");
            nrTesteCuSucces++;
        }
    }
