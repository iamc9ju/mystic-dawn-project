package tile;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[300];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        try {
            loadMap("/maps/Map01.txt", 0);
        } catch (Exception e) {
            System.out.println("Error loading Map01: " + e.getMessage());
        }

        try {
            loadMap("/maps/Map2.txt", 1);
        } catch (Exception e) {
            System.out.println("Error loading Map2: " + e.getMessage());
        }

        try {
            loadMap("/maps/Map3.txt", 2);
        } catch (Exception e) {
            System.out.println("Error loading Map3: " + e.getMessage());
        }

        try {
            loadMap("/maps/Map4.txt", 3);
        } catch (Exception e) {
            System.out.println("Error loading Map4: " + e.getMessage());
        }

        try {
            loadMap("/maps/Map5.txt", 4);
        } catch (Exception e) {
            System.out.println("Error loading Map5: " + e.getMessage());
        }

        //loadMap("/maps/interior01.txt",1);
    }

    public void getTileImage() {

        //PLACEHOLDER
//        setUp(0,"grass",false);
//        setUp(1,"wall",true);
//        setUp(2,"water",true);
//        setUp(3,"earth",false);
//        setUp(4,"tree",true);
//        setUp(5,"sand",false);
//        setUp(0,"grass",false);
//        setUp(1,"wall",true);
//        setUp(2,"water",true);
//        setUp(3,"earth",false);
//        setUp(4,"tree",true);
//        setUp(5,"sand",false);
        //PLACEHOLDER
        setUp(0,"earth",true);
        setUp(1,"floor1",true);
        setUp(2,"floor2",true);
        setUp(3,"grass00",false);
        setUp(4,"grass01",false);
        setUp(5,"hut",false);
        setUp(6,"newBarrier00",true);
        setUp(7,"newBarrier01",true);
        setUp(8,"newBarrier02",true);
        setUp(9,"newBarrier03",true);
        setUp(10,"newBarrier04",true);

        setUp(11,"newBarrier05",true);
        setUp(12,"newBarrier06",true);
        setUp(13,"newBarrier07",true);
        setUp(14,"newGrass00",false);
        setUp(15,"newGrass01",true);
        setUp(16,"newGrass02",true);
        setUp(17,"newGrass03",false);
        setUp(18,"newStairs00",false);
        setUp(19,"newStairs01",false);

        setUp(20,"newStairs02",false);
        setUp(21,"newStairs03",true);
        setUp(22,"newStairs04",true);
        setUp(23,"newStairs05",false);
        setUp(24,"newStairs06",true);
        setUp(25,"newStairs07",true);
        setUp(26,"newStairs08",true);
        setUp(27,"newStairs09",true);
        setUp(28,"newStairs10",false);
        setUp(29,"newStairs11",false);

        setUp(30,"newStairs12",false);
        setUp(31,"newStairs13",false);
        setUp(32,"newWallEarth00",true);
        setUp(33,"newWallEarth01",true);
        setUp(34,"newWallEarth02",true);
        setUp(35,"newWallEarth03",true);
        setUp(36,"newWallEarth04",true);
        setUp(37,"newWallEarth05",true);
        setUp(38,"newWallEarth06",true);
        setUp(39,"newWallEarth07",true);

        setUp(40,"newWallEarth08",true);
        setUp(41,"newWallEarth09",true);
        setUp(42,"newWallEarth10",true);
        setUp(43,"newWallEarth11",true);
        setUp(44,"newWallEarth12",true);
        setUp(45,"newWallEarth13",true);
        setUp(46,"newWallEarth14",true);
        setUp(47,"newWallEarth15",true);
        setUp(48,"newWallSea01",true);
        setUp(49,"newWallSea02",true);

        setUp(50,"newWallSea03",true);
        setUp(51,"newWallSea04",true);
        setUp(52,"newWallSea05",true);
        setUp(53,"newWallSea06",true);
        setUp(54,"newWallSea07",true);
        setUp(55,"newWallSea08",true);
        setUp(56,"newWater01",true);
        setUp(57,"newWater02",false);
        setUp(58,"newWater03",false);
        setUp(59,"newWater04",false);

        setUp(60,"newWaterDown",false);
        setUp(61,"newWaterLeft",false);
        setUp(62,"newWaterLeft01",false);
        setUp(63,"newWaterLeft02",false);
        setUp(64,"newWaterRight",false);
        setUp(65,"newWaterRight01",false);
        setUp(66,"newWaterRight02",false);
        setUp(67,"NewWaterUp",false);
        setUp(68,"road00",false);
        setUp(69,"road01",false);

        setUp(70,"road02",false);
        setUp(71,"road03",false);
        setUp(72,"road04",false);
        setUp(73,"road05",false);
        setUp(74,"road06",false);
        setUp(75,"road07",false);
        setUp(76,"road08",false);
        setUp(77,"road09",false);
        setUp(78,"road10",false);
        setUp(79,"road11",false);

        setUp(80,"road12",false);
        setUp(81,"table01",false);
        setUp(82,"tree",true);
        setUp(83,"wall",true);
        setUp(84,"water00",true);
        setUp(85,"water01",true);
        setUp(86,"water02",true);
        setUp(87,"water03",true);
        setUp(88,"water04",true);
        setUp(89,"water05",true);

        setUp(90,"water06",true);
        setUp(91,"water07",true);
        setUp(92,"water08",true);
        setUp(93,"water09",true);
        setUp(94,"water10",true);
        setUp(95,"water11",true);
        setUp(96,"water12",true);
        setUp(97,"water13",true);
        setUp(98,"ZANewGround00",false);
        setUp(99,"ZDungeon (1)",true);

        setUp(100,"ZDungeon (10)",false);
        setUp(101,"ZDungeon (11)",false);
        setUp(102,"ZDungeon (12)",false);
        setUp(103,"ZDungeon (13)",false);
        setUp(104,"ZDungeon (14)",true);
        setUp(105,"ZDungeon (15)",true);
        setUp(106,"ZDungeon (16)",true);
        setUp(107,"ZDungeon (17)",false);
        setUp(108,"ZDungeon (18)",true);
        setUp(109,"ZDungeon (19)",true);
        setUp(110,"ZDungeon (2)",true);

        setUp(111,"ZDungeon (20)",true);
        setUp(112,"ZDungeon (21)",true);
        setUp(113,"ZDungeon (22)",true);
        setUp(114,"ZDungeon (23)",true);
        setUp(115,"ZDungeon (24)",true);
        setUp(116,"ZDungeon (25)",true);
        setUp(117,"ZDungeon (26)",true);
        setUp(118,"ZDungeon (27)",true);
        setUp(119,"ZDungeon (28)",true);

        setUp(120,"ZDungeon (29)",true);
        setUp(121,"ZDungeon (3)",true);
        setUp(122,"ZDungeon (30)",true);
        setUp(123,"ZDungeon (31)",true);
        setUp(124,"ZDungeon (32)",true);
        setUp(125,"ZDungeon (33)",true);
        setUp(126,"ZDungeon (34)",true);
        setUp(127,"ZDungeon (35)",true);
        setUp(128,"ZDungeon (36)",true);
        setUp(129,"ZDungeon (37)",true);

        setUp(130,"ZDungeon (38)",true);
        setUp(131,"ZDungeon (39)",true);
        setUp(132,"ZDungeon (4)",true);
        setUp(133,"ZDungeon (40)",true);
        setUp(134,"ZDungeon (41)",true);
        setUp(135,"ZDungeon (42)",true);
        setUp(136,"ZDungeon (43)",true);
        setUp(137,"ZDungeon (44)",true);
        setUp(138,"ZDungeon (45)",true);
        setUp(139,"ZDungeon (46)",false);

        setUp(140,"ZDungeon (47)",false);
        setUp(141,"ZDungeon (48)",false);
        setUp(142,"ZDungeon (49)",false);
        setUp(143,"ZDungeon (5)",true);
        setUp(144,"ZDungeon (50)",true);
        setUp(145,"ZDungeon (51)",true);
        setUp(146,"ZDungeon (52)",true);
        setUp(147,"ZDungeon (53)",true);
        setUp(148,"ZDungeon (54)",true);
        setUp(149,"ZDungeon (55)",true);
        setUp(150,"ZDungeon (56)",true);


        setUp(151,"ZDungeon (57)",true);
        setUp(152,"ZDungeon (58)",true);
        setUp(153,"ZDungeon (59)",true);
        setUp(154,"ZDungeon (6)",true);
        setUp(155,"ZDungeon (60)",true);
        setUp(156,"ZDungeon (61)",true);
        setUp(157,"ZDungeon (62)",true);
        setUp(158,"ZDungeon (7)",true);
        setUp(159,"ZDungeon (8)",true);
        setUp(160,"ZDungeon (9)",false);
        setUp(161,"ZFHouse (1)",true);
        setUp(162,"ZFHouse (10)",true);
        setUp(163,"ZFHouse (11)",true);
        setUp(164,"ZFHouse (12)",true);
        setUp(165,"ZFHouse (13)",true);
        setUp(166,"ZFHouse (14)",true);
        setUp(167,"ZFHouse (15)",true);
        setUp(168,"ZFHouse (16)",true);
        setUp(169,"ZFHouse (17)",true);
        setUp(170,"ZFHouse (18)",true);
        setUp(171,"ZFHouse (19)",true);
        setUp(172,"ZFHouse (2)",true);
        setUp(173,"ZFHouse (20)",true);
        setUp(174,"ZFHouse (21)",true);
        setUp(175,"ZFHouse (22)",true);
        setUp(176,"ZFHouse (23)",true);
        setUp(177,"ZFHouse (24)",true);
        setUp(178,"ZFHouse (25)",true);
        setUp(179,"ZFHouse (26)",true);
        setUp(180,"ZFHouse (27)",true);
        setUp(181,"ZFHouse (28)",true);
        setUp(182,"ZFHouse (29)",true);
        setUp(183,"ZFHouse (3)",true);
        setUp(184,"ZFHouse (30)",true);
        setUp(185,"ZFHouse (31)",true);
        setUp(186,"ZFHouse (32)",true);
        setUp(187,"ZFHouse (33)",true);
        setUp(188,"ZFHouse (34)",true);
        setUp(189,"ZFHouse (35)",true);
        setUp(190,"ZFHouse (36)",true);
        setUp(191,"ZFHouse (37)",true);
        setUp(192,"ZFHouse (38)",true);
        setUp(193,"ZFHouse (39)",true);
        setUp(194,"ZFHouse (4)",true);
        setUp(195,"ZFHouse (40)",true);
        setUp(196,"ZFHouse (41)",true);
        setUp(197,"ZFHouse (42)",true);
        setUp(198,"ZFHouse (43)",true);
        setUp(199,"ZFHouse (44)",true);
        setUp(200,"ZFHouse (45)",true);
        setUp(201,"ZFHouse (46)",true);
        setUp(202,"ZFHouse (47)",true);
        setUp(203,"ZFHouse (48)",true);
        setUp(204,"ZFHouse (49)",true);
        setUp(205,"ZFHouse (5)",true);
        setUp(206,"ZFHouse (50)",true);
        setUp(207,"ZFHouse (51)",true);
        setUp(208,"ZFHouse (52)",true);
        setUp(209,"ZFHouse (53)",true);
        setUp(210,"ZFHouse (54)",true);
        setUp(211,"ZFHouse (55)",true);
        setUp(212,"ZFHouse (56)",true);
        setUp(213,"ZFHouse (57)",true);
        setUp(214,"ZFHouse (58)",true);
        setUp(215,"ZFHouse (59)",true);
        setUp(216,"ZFHouse (6)",true);
        setUp(217,"ZFHouse (60)",true);
        setUp(218,"ZFHouse (7)",true);
        setUp(219,"ZFHouse (8)",true);
        setUp(220,"ZFHouse (9)",true);

        setUp(221,"Zhouse_01",true);
        setUp(222,"Zhouse_02",true);
        setUp(223,"Zhouse_03",true);
        setUp(224,"Zhouse_04",true);
        setUp(225,"Zhouse_05",true);

        setUp(226,"Zhouse_06",true);
        setUp(227,"Zhouse_07",true);
        setUp(228,"Zhouse_08",true);
        setUp(229,"Zhouse_09",true);
        setUp(230,"Zhouse_10",true);
        setUp(231,"Zhouse_11",true);
        setUp(232,"Zhouse_12",true);
        setUp(233,"Zhouse_13",true);
        setUp(234,"Zhouse_14",true);
        setUp(235,"Zhouse_15",true);
        setUp(236,"Zhouse_16",true);
        setUp(237,"Zhouse_17",true);
        setUp(238,"Zhouse_18",true);

        setUp(239,"Zhouse_19",true);
        setUp(240,"Zhouse_20",true);
        setUp(241,"Zhouse_21",true);
        setUp(242,"Zhouse_22",true);
        setUp(243,"Zhouse_23",true);
        setUp(244,"Zhouse_24",true);
        setUp(245,"Zhouse_25",true);
        setUp(246,"Zhouse_26",true);
        setUp(247,"Zhouse_27",false);
        setUp(248,"Zhouse_28",false);
        setUp(249,"Zhouse_29",true);
        setUp(250,"Zhouse_30",true);
        setUp(251,"ZnameBoat_01",false);
        setUp(252,"ZnameBoat_02",false);

        setUp(253,"ZnameBoat_03",false);
        setUp(254,"ZnameBoat_04",true);
        setUp(255,"ZnameBoat_05",true);
        setUp(256,"ZnameBoat_06",true);
        setUp(257,"Znboat (1)",true);
        setUp(258,"Znboat (2)",false);
        setUp(259,"Znboat (3)",false);
        setUp(260,"Znboat (4)",false);
        setUp(261,"Znboat (5)",true);
        setUp(262,"Znboat (6)",true);
        setUp(263,"Znboat (7)",true);

    }
    public void setUp(int index,String imageName,boolean collision){

        UtilityTool utilityTool = new UtilityTool();
        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName +".png"));
            tile[index].image = utilityTool.scaleImage(tile[index].image,gp.tileSize,gp.tileSize);
            tile[index].collision = collision;
        }catch (IOException e){
            System.out.println("หารูปไม่เจอ");
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath,int map) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while(col < gp.maxWorldCol) {
                    String[] numbers = line.split(" "); //put number in text file into number array and split space between number
                    int num = Integer.parseInt(numbers[col]); //change string to integer

                    mapTileNum[map][col][row] = num;
                    col++;

                }
                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close(); //scan text file line by line and get them to numbers[]
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;


        while(worldCol < gp.maxWorldCol &&  worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow]; //extract mapTileNum to tileNum

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX  - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize< gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize> gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.worldY) {
                g2.drawImage(tile[tileNum].image,screenX,screenY,null); //tile arry
            }
            worldCol++;

            if(worldCol == gp.maxWorldCol) { //reset column
                worldCol = 0;
                worldRow++;

            }
        }
    }
}
