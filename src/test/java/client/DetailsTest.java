// package client;

// import org.junit.jupiter.api.Test;

// import client.Recipe;
// import client.RecipeList;

// import org.junit.jupiter.api.BeforeEach;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertThrows;

// public class DetailsTest {
//     Details details;

//     @Test
//     public void testSetandGetTitle(){
//         details.setTitle("Beef Noodle Soup");
//         assertEquals("Beef Noodle Soup", details.getTitle());
//     }

//     @Test
//     public void testSetandGetDetails(){
//         details.setDetails("Cook beef and put it in warm noodle soup");
//         assertEquals("Cook beef and put it in warm noodle soup", details.getDetails());
//     }

//     @Test
//     public void testEditDetails(){
//         details.setTitle("Beef Noodle Soup");
//         details.setDetails("Cook beef and put it in warm noodle soup");
//         assertEquals("Beef Noodle Soup", details.getTitle());
//         assertEquals("Cook beef and put it in warm noodle soup", details.getDetails());

//         details.setTitle("Chicken Noodle Soup");
//         details.setDetails("Cook chicken and put it in warm noodle soup");
//         assertEquals("Chicken Noodle Soup", details.getTitle());
//         assertEquals("Cook chicken and put it in warm noodle soup", details.getDetails());
//     }


// }