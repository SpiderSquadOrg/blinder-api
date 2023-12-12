package com.blinder.api.Music.mapper;

import com.blinder.api.Music.dto.MusicResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class MusicCustomMapper {
    public List<MusicResponseDto> musicDataToMusicResponseDtos(Mono<String> musicData) throws JsonProcessingException {
        List<MusicResponseDto> musicResponseDtos = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = musicData.block();

        JsonNode jsonNode = objectMapper.readTree(jsonString);

        JsonNode items = jsonNode.get("tracks").get("items");

        items.forEach((item) -> {
            MusicResponseDto musicResponseDto = new MusicResponseDto();
            musicResponseDto.setSpotifyId(item.get("id").asText());
            musicResponseDto.setName(item.get("name").asText());

            List<String> artists = new ArrayList<>();
            item.get("artists").forEach((artist) -> artists.add(artist.get("name").asText()));
            musicResponseDto.setArtists(artists);
            musicResponseDto.setAlbum(item.get("album").get("name").asText());
            musicResponseDto.setImage(item.get("album").get("images").get(0).get("url").asText());

            musicResponseDtos.add(musicResponseDto);
        });

        return musicResponseDtos;
    }

    public MusicResponseDto musicDataToMusicResponseDto(Mono<String> musicData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = musicData.block();

        JsonNode item = objectMapper.readTree(jsonString);

        MusicResponseDto musicResponseDto = new MusicResponseDto();
        musicResponseDto.setSpotifyId(item.get("id").asText());
        musicResponseDto.setName(item.get("name").asText());

        List<String> artists = new ArrayList<>();
        item.get("artists").forEach((artist) -> artists.add(artist.get("name").asText()));
        musicResponseDto.setArtists(artists);
        musicResponseDto.setAlbum(item.get("album").get("name").asText());
        musicResponseDto.setImage(item.get("album").get("images").get(0).get("url").asText());


        return musicResponseDto;
    }
}