package com.game.service;

import com.game.data.DirectionEnum;
import com.game.data.Island;
import com.game.data.Viking;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Service for some action with different entities
 */
public class ActionService {


    /**
     * Finding islands are destroyed by Vikings. In the cases when two or more vikings land on one Island.
     * When Island is destroyed remove directions from other islands to the destroyed island.
     * When Island is destroyed viking's ships also are destroyed, so they are removed from the game
     * <p>
     * And finding vikings that stuck on the Islands because of all direction from current Islands are closed
     *
     * @param gameIslands List of Islands on the map
     * @param gameVikings List of vikings on the map (in the game)
     */
    public static void removeIslandsVikings(List<Island> gameIslands, List<Viking> gameVikings) {


        //Find Islands for remove because on one island more than one viking
        List<Island> islandsForRemove = gameVikings.stream()
                .collect(Collectors.groupingBy(Viking::getIsland, Collectors.counting()))
                .entrySet().stream()
                .filter(islandLongEntry -> islandLongEntry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        //Build Map of islands and Vikings because of which we close islands
        Map<Island, List<Viking>> islandVikingForRemove = new HashMap<>();
        for (Island island : islandsForRemove) {
            islandVikingForRemove.put(island, gameVikings
                    .stream()
                    .filter(viking -> viking.getIsland().equals(island))
                    .collect(Collectors.toList()));
        }

        //Write message about closed islands and vikings
        islandVikingForRemove.forEach((island, vikings) -> {
            String destroyerVikings = vikings.stream().map(Viking::getName).collect(Collectors.joining(", "));
            System.out.println("AGR!!! On the " + island.getName() + ", the lighthouse was destroyed, thanks to " + destroyerVikings);
        });


        //Remove destroyed islands from list of game islands
        gameIslands.removeAll(islandsForRemove);

        //Clear directions of Islands
        for (Island gameIsland : gameIslands) {
            for (Iterator<Map.Entry<DirectionEnum, String>> iterator = gameIsland.getDirection().entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<DirectionEnum, String> entry = iterator.next();
                String islandName = entry.getValue();
                if (islandsForRemove.stream().anyMatch(island1 -> island1.getName().equals(islandName))) {
                    iterator.remove();
                }
            }

        }

        //remove vikings from the game map because their ships was destroyed
        gameVikings.removeAll(islandVikingForRemove
                .values()
                .stream()
                .collect(
                        HashSet<Viking>::new,
                        AbstractCollection::addAll,
                        HashSet::addAll)
        );

        //remove vikings that was stuck on the island
        for (Iterator<Viking> iterator = gameVikings.iterator(); iterator.hasNext(); ) {
            Viking viking = iterator.next();
            if (viking.getIsland().getDirection() == null || viking.getIsland().getDirection().size() == 0) {
                System.out.println("AGR!!! " + viking.getName() + " is stranded on the " + viking.getIsland().getName() +
                        " and no longer participates in the war");
                iterator.remove();
            }
        }
    }

    /**
     * Move of viking depends on his opened directions
     *
     * @param gameIslands List of Islands on the map
     * @param viking      Viking which we want to move
     */
    private static void vikingLocomotion(List<Island> gameIslands, Viking viking) {

        //randomly choose another opened island for viking
        String randomlyChosenDirection = new ArrayList<>(viking.getIsland().getDirection().values()).get(new Random().nextInt(viking.getIsland().getDirection().values().size()));
        //Find island with such name and update Island of viking (move viking)
        gameIslands.stream()
                .filter(island -> island.getName().equals(randomlyChosenDirection))
                .findAny()
                .ifPresent(viking::setIsland);

    }


    /**
     * Run the iteration of 10000 days and move vikings between islands
     * After that remove unnecessary vikings and islands from the map
     * Check if vikings or islands are empty
     *
     * @param gameIslands List of Islands on the map
     * @param gameVikings List of vikings on the map (in the game)
     * @return Message about ending of the game
     */
    public static String warCycle(List<Island> gameIslands, List<Viking> gameVikings) {

        //We have just 10000 days for our war
        for (int i = 1; i < 10000 + 1; i++) {

            //On each step of war cycle every viking must move
            for (Viking gameViking : gameVikings) {
                vikingLocomotion(gameIslands, gameViking);
            }

            //After movement of vikings we check islands and vikings for remove
            removeIslandsVikings(gameIslands, gameVikings);

            if (gameVikings.size() == 0) {
                return "GAME OVER : All Vikings were destroyed or stranded! ";
            }

            if (gameIslands.size() == 0) {
                return "GAME OVER : All Islands were destroyed! ";
            }

        }

        return "GAME OVER : Vikings needed to stock up on more food";


    }

}
