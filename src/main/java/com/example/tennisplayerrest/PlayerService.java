package com.example.tennisplayerrest;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    //method to return all players
    public List<Player> getAllPlayers() {
        //call repository method
        return playerRepository.findAll();
    }

    //method to find player by id
    public Player getPlayer(int id) {
       Optional<Player> tempPlayer = playerRepository.findById(id);


       if(tempPlayer.isEmpty())
           throw new PlayerNotFoundException("Player with id:" + id + " not found.");

       return tempPlayer.get();
    }

    //method to add player
    public Player addPlayer(Player p) {
        return playerRepository.save(p);
    }
    public Player updatePlayer(int id, Player p) {
        Player player = playerRepository.getOne(id);
        player.setName(p.getName());
        player.setName(p.getName());
        player.setNationality(p.getNationality());
        player.setBirthDate(p.getBirthDate());
        player.setTitles(p.getTitles());
        return playerRepository.save(player);
    }

    public Player patch(int id, Map<String, Object> playerPatch) {
        Optional<Player> player = playerRepository.findById(id);

        if(player.isPresent()) {
            playerPatch.forEach( (key, value) -> {
                Field field = ReflectionUtils.findField(Player.class, key);
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, player.get(), value);
            });
        } else
            throw new PlayerNotFoundException("Player with id { "+ id +" } not found");
        return playerRepository.save(player.get());
    }
    @Transactional
    public void updateTitles(int id, int titles) {
        playerRepository.updateTitles(id, titles);
    }

    public String deletePlayer(int id) {
        try {
        playerRepository.deleteById(id);
        } catch (Exception e) {
            return "Player with id: " + id + "not found";
        }
        return "Deleted player with id: " + id;
    }
}
