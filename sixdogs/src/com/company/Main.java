package com.company;

import java.util.*;
import java.util.Map;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
 //THe purpose of this code is to find sixdegrees of separation of a txt file with tweet mentions.
 //Since this is a classic graph problem, the implementation of the graph was used from princeton universitly files (Graph,Pathfinder,Queue,Stack)
 //Not all files are used , however for compiling reasons are kept untouched, except from Pathfinder.java which was modified to meet our needs.
 public class Main
 {
     //Helper function that has input a hashmap of keys with multiple values and a value and returns the list of keys that correspond to it.
     public static List<String> getKeyFromValue(Map hm, Object value) {
         List<String> keys = new ArrayList<String>();
         for (Object o : hm.keySet()) {
             if (hm.get(o).equals(value)) {
                 keys.add(o.toString());
             }
         }
         return keys;
     }


     public static String readFile(String fileName) throws IOException {
         BufferedReader br = new BufferedReader(new FileReader(fileName));
         try {
             StringBuilder sb = new StringBuilder();
             String line = br.readLine();

             while (line != null) {
                 sb.append(line);
                 sb.append("\n");
                 line = br.readLine();
             }
             return sb.toString();
         } finally {
             br.close();
         }
     }

     public static void write (String filename, String what) throws IOException{
         BufferedWriter outputWriter = null;
         outputWriter = new BufferedWriter(new FileWriter(filename));
             // Maybe:
             //    outputWriter.write(x[i]+"");
             // Or:
             outputWriter.write(what);
             outputWriter.newLine();

         outputWriter.flush();
         outputWriter.close();
     }


     public static void main(String[] args) {
     int l = 0;
     int i = 0;
     int m = 0;


     String myfile = "daniella_hamill: @madelyn, @concepcion_hoppe: Power is in nature the essential measure  of right\n" +
             "kaia_jerde: Corresponding abstract name /cc @nella_hackett, @madelyn, @vincent\n" +
             "delaney_ferry: @brook_metz: I do not readily remember any poem, play, sermon, novel, or oration, that our press vents in the last few years\n" +
             "mariana: this makes botany the most alluring of studies, and wins it from the farmer and the herb-woman :D /cc @terrell_senger, @florence_vonrueden\n" +
             "mariana: In nature every moment is new; the past is always swallowed and forgotten /cc @leon_greenfelder, @darrel_skiles, @madelyn\n" +
             "nella_hackett: @daniella_hamill, @madelyn: After graduating from college he taught school for a time, and then entered the Harvard Divinity School\n" +
             "hilton: Nothing can bring you peace but yourself\n" +
             "magali: @kaia_jerde, @florence_vonrueden: The universe is fluid and volatile\n" +
             "terrell_senger: These varieties are lost sight of at a little distance, at a little height of thought @emiliano_gaylord\n" +
             "kaia_jerde: If the gatherer gathers too much, nature takes out of the man what she puts into his chest, swells the estate, but kills the owner /cc\n" +
             "terrell_senger: @nella_hackett It's become commonplace to criticize the \"Occupy\" movement for failing to offer an alternative vision\n" +
             "delaney_ferry: Chaucer's chief work is the \"Canterbury Tales,\" a series of  stories told by pilgrims traveling /cc @concepcion_hoppe, @mariana, @daniella_hamill\n" +
             "concepcion_hoppe: The greatest of Italian poets, author of the Divina Commedia /cc @mariana\n" +
             "leta: Emerson here refers to the military operations carried on from 1808 to 1814 in Portugal, Spain, and ... /cc @terrell_senger, @darrel_skiles\n" +
             "emiliano_gaylord: If a man loses his balance and immerses himself in any trades or pleasures for their own sake, he may be a good wheel or pin, but he is ...\n" +
             "darrel_skiles: Society is a joint-stock company, in which the members agree, for the better securing of his bread to each shareholder /cc @concepcion_hoppe\n" +
             "emiliano_gaylord: The Colossus of Rhodes was a gigantic statue--over a hundred feet in height--of the Rhodian sun god /cc @daniella_hamill, @elmo\n" +
             "magali: @madelyn: He carried his powerful execution into minute details, to a hair point; finishes an eyelash or a dimple as firmly as he draws a...\n" +
             "darrel_skiles: Each new step we take in thought reconciles twenty seemingly  discordant facts, as expressions of one law\n" +
             "lawson_brown: The pastures are full of ghosts for me, the morning woods full of angels /cc @emiliano_gaylord, @elmo\n" +
             "darrel_skiles: @lawson_brown, @brook_metz: Must we have a good understanding with one another's palates? as foolish people who have lived long together...\n" +
             "elmo: Each returns to his degree in the scale of good society, porcelain remains porcelain /cc @delaney_ferry, @magali, @terrell_senger\n" +
             "leta: The retribution in the circumstance is seen by the understanding; it is inseparable from the thing, but is often spread over a long time\n" +
             "lawson_brown: @leon_greenfelder: So, 'tis well; Never one object underneath the sun  Will I behold before my Sophocles: Farewell; now teach the Romans ...\n" +
             "magali: The swindler swindles himself\n" +
             "concepcion_hoppe: @magali FranÃ§ois Joseph Talma was a French tragic actor, to whom Napoleon showed favor\n" +
             "daniella_hamill: @cruz_rice, @brook_metz, @emiliano_gaylord: It was as a record of personal experiences that he wrote in his journal: \"Shakespeare fills us...\n" +
             "elmo: @hilton: His manner was very quiet, his smile was pleasant, but he did not like explosive laughter any better than Hawthorne did\n" +
             "darrel_skiles: @cruz_rice, @leon_greenfelder, @hilton: \"Blessed be nothing\" and \"The worse things are, the better they are\" are proverbs ...\n" +
             "daniella_hamill: @leon_greenfelder: This old proverb is quoted by Sophocles\n" +
             "leta: Heroism feels and never reasons, and therefore is always right; and although a different breeding, different /cc @nella_hackett, @delaney_ferry\n" +
             "mariana: @kaia_jerde, @elmo: why?\n" +
             "daniella_hamill: @florence_vonrueden: Translated in the previous sentence\n" +
             "hilton: not sure about that /cc @emiliano_gaylord\n" +
             "madelyn: It began with Henry VII /cc @nella_hackett\n" +
             "madelyn: @darrel_skiles, @emiliano_gaylord, @nella_hackett: To ignorance and sin it is flint\n" +
             "leon_greenfelder: @mariana: The Shakspeare Society have inquired in all directions, advertised the missing facts, offered money for any information that will\n" +
             "vincent: A man is reputed to have thought and eloquence; he cannot, for all that, say a word to his cousin or his uncle\n" +
             "hilton: John Lydgate was an English poet who lived a generation later than Chaucer; in his _Troy Book_ and other poems he probably borrowed fro...\n" +
             "nella_hackett: @brook_metz: I see\n" +
             "florence_vonrueden: @aomame A Persian poet of the fourteenth century\n" +
             "vincent: @delaney_ferry, @kaia_jerde, @brook_metz: He recreates what he imitates\n" +
             "madelyn: The first in time and the first in importance of the influences upon  the mind is that of nature /cc @emiliano_gaylord\n" +
             "florence_vonrueden: The blindness of the preacher consisted in deferring to the base estimate of the market of what constitutes a manly success /cc @hilton\n" +
             "delaney_ferry: @kaia_jerde, @terrell_senger: He claps wings to the sides of all the solid old lumber of the world, and I am capable once more of ...\n" +
             "leon_greenfelder: @mariana, @delaney_ferry: The work he is best known by is the exhaustive \"Essay on the Human Understanding,\" in which he combated the theo...\n" +
             "kaia_jerde: We rapidly approach a brink over which no enemy can follow us /cc @nella_hackett, @terrell_senger, @madelyn\n" +
             "hilton: The stag in the fable admired his horns and blamed his feet, but when the hunter came, his feet saved him ... /cc @kaia_jerde\n" +
             "leon_greenfelder: In punishment for this Jupiter chained him to a rock and set  an eagle to prey upon his liver /cc @nella_hackett\n" +
             "concepcion_hoppe: Each man's rank in that perfect graduation depends on some symmetry in his structure, or some agreement in ... /cc @nella_hackett, @leta\n" +
             "leon_greenfelder: Follow the path your genius traces like the galaxy of  heaven for you to walk in /cc @leta, @cruz_rice\n" +
             "daniella_hamill: Jupiter, the supreme god of Roman mythology /cc @emiliano_gaylord, @madelyn\n" +
             "mariana: @nella_hackett: The same idea exalts conversation with him\n" +
             "cruz_rice: Information about the Project Gutenberg Literary Archive  Foundation /cc @lawson_brown, @mariana\n" +
             "emiliano_gaylord: The prudence which secures an outward well-being is not to be studied by one set of men, whilst heroism /cc @nella_hackett, @florence_vonrueden\n" +
             "leta: Many economists say that what matters are questions like whether markets are competitive or monopolistic /cc @cruz_rice, @delaney_ferry, @leon_greenfelder\n" +
             "emiliano_gaylord: We can find these enchantments without visiting the Como  Lake, or the Madeira Islands /cc @elmo, @lawson_brown, @vincent\n" +
             "elmo: A boy is in the parlor what the pit is in the playhouse; independent, irresponsible, looking out from his corner ... /cc @hilton, @magali\n" +
             "concepcion_hoppe: @vincent, @leon_greenfelder, @daniella_hamill: To refuse to discuss ideas such as types of capitalism deprives us of language\n" +
             "kaia_jerde: @emiliano_gaylord After every foolish day we sleep off the fumes and furies of its hours; and though we are always engaged with particulars...\n" +
             "hilton: You will find it interesting to read _Behavior_ in connection with this essay /cc @brook_metz\n" +
             "florence_vonrueden: @elmo, @kaia_jerde, @delaney_ferry: The swallow over my window should interweave that thread or straw he carries in his bill into my web ...\n" +
             "lawson_brown: @kaia_jerde, It is, for my money, a perfect opening: fast, funny, suspenseful\n" +
             "leon_greenfelder: @elmo But if we explore the literature of Heroism, we shall quickly come to Plutarch, who is its Doctor and historian\n" +
             "elmo: @lawson_brown? is a great happiness to get off without injury and heart-burning, from one who has had the ill luck to be ...\n" +
             "emiliano_gaylord: Our housekeeping is mendicant, our arts, our occupations,  our marriages, our religion, we have not chosen, but society has chosen for us\n" +
             "leon_greenfelder: @madelyn: What is the end sought? Plainly to secure the ends of good sense and beauty, from the intrusion of deformity or vulgarity of ...\n" +
             "vincent: If I know your sect, I anticipate your argument /cc @madelyn\n" +
             "darrel_skiles: @nella_hackett, @mariana: Here is perfect representation, at last; and now let the world of figures sit for their portraits\n" +
             "darrel_skiles: @magali, @leta: But though we cannot find the god under this disguise of a sutler, yet, on the other hand, we cannot forgive the poet\n" +
             "nella_hackett: @cruz_rice: Were the ends of nature so great and cogent, as to  exact this immense sacrifice of men?\n" +
             "delaney_ferry: @leon_greenfelder, @emiliano_gaylord, @mariana: A play by the German poet, Goethe, founded on the belief that the imprisonment of Tasso was...\n" +
             "delaney_ferry: Thatâ€™s a nod to science fiction, where the archetypal marker of strangeness is two moons /cc @leon_greenfelder\n" +
             "cruz_rice: See note 53 @daniella_hamill, @emiliano_gaylord\n" +
             "florence_vonrueden: The Germans regard Goethe with the same veneration we accord to Shakespeare /cc @nella_hackett, @magali\n" +
             "brook_metz: This is fundamentally a detective story, albeit a distinctly heterodox one /cc @darrel_skiles, @madelyn\n" +
             "delaney_ferry: @lawson_brown: A preface or introduction";

     //For the parsing of the file a hashmap is used to keep the values of people who mention as keys,and people who are mentioned as values.
     String lines[] = myfile.split("\\r?\\n");
     String substrng = "";
     HashMap<String, List<String>> names = new HashMap<String, List<String>>();

     for (int j = 0; j < lines.length; j++) {
         i = lines[j].indexOf(':');
         List<String> values = new ArrayList<String>();
         substrng = lines[j].substring(0, i);
         m = lines[j].indexOf('@');

         while (m >= 0) {
             String intermediate = lines[j].substring(m + 1);
             String firstonly[] = intermediate.split("[!,.:?; \n]");
             values.add(firstonly[0]);
             m = lines[j].indexOf('@', m + 1);
         }

         List<String> list = new ArrayList<String>();
         if (names.containsKey(substrng)) {
             list = names.get(substrng);
             for (l = 0; l < list.size(); l++) {
                 values.add(list.get(l));}
             names.put(substrng, values);}
         else {
             names.put(substrng, values);
         }
     }
     //System.out.println(names);
     //The parsing is finished, so a new sorted alphabetically treemap keeps the first order connections, that are necessary to feed our graph
     TreeMap<String, List<String>> firstconnection = new TreeMap<>();
     TreeMap<String, Integer> activeusers = new TreeMap<>();
     for (String key : names.keySet()) {
         activeusers.put(key, 1);
         List<String> list = new ArrayList<String>();
         list = names.get(key);
         List<String> listfirstconnections = new ArrayList<String>();
         for (l = 0; l < list.size(); l++) {
             String mention=list.get(l);
             if (names.containsKey(mention)&&(names.get(mention).contains(key))) {
                 listfirstconnections.add(mention);
                 firstconnection.put(key, listfirstconnections);
             }
         }
     }

   //  System.out.println(firstconnection);
        // try {
           //  PrintWriter writer = new PrintWriter("D:\\romina.txt", "UTF-8");
            // File f = new File("D:\\complex_output13.txt");
            // write("complex_output1.txt", s);
       //  }
       //  catch (Exception e) {
       //      System.out.println(e.getClass());
       //  }
         try {
             BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\complex_output.txt",true ));

     //The Graph is implemented from Princeton university and slightly modified to be given right input (our previons firstconnection treemap)
     Graph G = new Graph (firstconnection);
     for ( String s : activeusers.keySet() ) {
         System.out.println(System.lineSeparator() + s);
         writer.write( System.lineSeparator()  );
               writer.write( s );
               writer.flush();
               writer.newLine();
         if (firstconnection.containsKey(s)) {
             HashMap<String, Integer> toprint = new HashMap<String, Integer>();
             PathFinder pf = new PathFinder(G, s);
             for (String key : firstconnection.keySet()) {
                 String t = key;
                 for (String v : pf.pathTo(t)) {
                     if ((s != v) && pf.distanceTo(t) != 0) {
                         if (toprint.containsKey(t)) {

                             if (toprint.get(t) < pf.distanceTo(t))
                                 toprint.put(t, pf.distanceTo(t));
                         } else
                             toprint.put(t, pf.distanceTo(t));
                     }
                 }
             }
             //Unfortunately we have to clarify how many degrees of connections we want to print...
             for (int n = 1; n < 9; n++) {
                 List<String> connections = new ArrayList<String>();
                 connections = getKeyFromValue(toprint, n);
                 Collections.sort(connections);
                 if (!connections.isEmpty()) {
                     StringBuilder builder = new StringBuilder();
                     for (String value : connections) {
                         builder.append(value + ", ");
                     }
                     String text = builder.toString();
                     text = text.replaceAll(", $", "");
                     System.out.println(text);
                     writer.write( text );
                     writer.flush();
                     writer.write("\n");
                     writer.newLine();

                 }
             }

         }
     }
             writer.close();
             }
         catch (Exception e) {
                System.out.println(e.getClass());
               }

 }
 }






