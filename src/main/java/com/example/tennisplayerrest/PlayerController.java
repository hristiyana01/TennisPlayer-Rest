package com.example.tennisplayerrest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @GetMapping("/welcome")
    public String welcome() {
        return "Tennis Player Rest API";
    }

    @GetMapping("/players")
    public List<Player> getAllPlayers() {
        //call service layer method
       return playerService.getAllPlayers();
    }

    @GetMapping("/players/{id}")
    public Player getPlayer(@PathVariable int id) {
        return playerService.getPlayer(id);
    }

    @PostMapping("/players")
    public Player addPlayer(@RequestBody Player player) {
        player.setId(0);
        return playerService.addPlayer(player);
    }

    @PutMapping("/players/{id}")
    public Player updatePlayer( @RequestBody Player player, @PathVariable int id) {
        return playerService.updatePlayer(id, player);
    }

    @PatchMapping("/players/{id}")
    public Player partialUpdate(@PathVariable int id,
                                @RequestBody Map<String, Object> playerPatch) {
        return playerService.patch(id, playerPatch);
    }
    @PatchMapping("/players/{id}/titles")
    public void updateTitles(@PathVariable int id, @RequestBody int titles) {
        playerService.updateTitles(id, titles);
    }

    @DeleteMapping("/players/{id}")
    public String deletePlayer(@PathVariable int id) {
        return playerService.deletePlayer(id);
    }
}
