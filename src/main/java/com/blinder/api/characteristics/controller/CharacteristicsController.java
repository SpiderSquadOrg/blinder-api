package com.blinder.api.characteristics.controller;

import com.blinder.api.Movie.dto.CreateMovieRequestDto;
import com.blinder.api.Movie.mapper.MovieMapper;
import com.blinder.api.Music.dto.CreateMusicRequestDto;
import com.blinder.api.Music.mapper.MusicMapper;
import com.blinder.api.MusicCategory.dto.CreateMusicCategoryRequestDto;
import com.blinder.api.MusicCategory.mapper.MusicCategoryMapper;
import com.blinder.api.characteristics.dto.CharacteristicsResponseDto;
import com.blinder.api.characteristics.dto.CreateCharacteristicsRequestDto;
import com.blinder.api.characteristics.dto.UpdateCharacteristicsRequestDto;
import com.blinder.api.characteristics.mapper.CharacteristicsMapper;
import com.blinder.api.characteristics.model.Characteristics;
import com.blinder.api.characteristics.service.CharacteristicsService;
import com.blinder.api.hobby.dto.CreateHobbyRequestDto;
import com.blinder.api.hobby.mapper.HobbyMapper;
import com.blinder.api.user.model.User;
import com.blinder.api.user.security.auth.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/characteristics")
@RestController
@RequiredArgsConstructor
public class CharacteristicsController {
    private final CharacteristicsService characteristicsService;
    private final UserAuthService userAuthService;

    @GetMapping
    @Operation(summary = "Get all characteristics")
    public ResponseEntity<Page<CharacteristicsResponseDto>> getAllCharacteristics(@RequestParam(name = "page", required = false) Integer page,
                                                                                  @RequestParam(name = "size", required = false) Integer size) {
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristicsService.getCharacteristics(page, size)), HttpStatus.OK);
    }

    @GetMapping("/byUser")
    @Operation(summary = "Get characteristics by userId")
    public ResponseEntity<CharacteristicsResponseDto> getCharacteristicsByUserId() {
        User currentUser = userAuthService.getActiveUser().getUser();
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristicsService.getCharacteristicsByUserId(currentUser.getId())), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add new characteristics")
    public ResponseEntity<CreateCharacteristicsRequestDto> addReport(@RequestBody CreateCharacteristicsRequestDto createCharacteristicsRequestDto) {
        characteristicsService.addCharacteristics(CharacteristicsMapper.INSTANCE.createCharacteristicsRequestDtoToCharacteristics(createCharacteristicsRequestDto));
        return new ResponseEntity<>(createCharacteristicsRequestDto, HttpStatus.CREATED);
    }

    @PutMapping("/{characteristicsId}")
    @Operation(summary = "Update characteristics")
    public ResponseEntity<UpdateCharacteristicsRequestDto> updateCharacteristics(@PathVariable(name = "characteristicsId") String characteristicsId,
                                                                        @RequestBody UpdateCharacteristicsRequestDto updateCharacteristicsRequestDto) {
        characteristicsService.updateCharacteristics(characteristicsId, CharacteristicsMapper.INSTANCE.updateCharacteristicsRequestDtoToCharacteristics(updateCharacteristicsRequestDto));
        return new ResponseEntity<>(updateCharacteristicsRequestDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{characteristicsId}")
    @Operation(summary = "Delete characteristics")
    public ResponseEntity<HttpStatus> deleteCharacteristics(@PathVariable(name = "characteristicsId") String characteristicsId) {
        characteristicsService.deleteCharacteristics(characteristicsId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Music
    @PatchMapping("/musics")
    @Operation(summary = "Add music to user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> addToMusicList(@RequestBody CreateMusicRequestDto createMusicRequestDto){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.addToMusicList(currentUser.getId(), MusicMapper.INSTANCE.createMusicRequestDtoToMusic(createMusicRequestDto));
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.CREATED);
    }

    @PatchMapping("/musics/{musicId}")
    @Operation(summary = "Remove music from user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> removeFromMusicList(@PathVariable String musicId){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.removeFromMusicList(currentUser.getId(), musicId);
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.NO_CONTENT);
    }

    //Music category
    @PatchMapping("/musics/categories")
    @Operation(summary = "Add music category to user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> addToMusicCategoryList(@RequestBody CreateMusicCategoryRequestDto createMusicCategoryRequestDto){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.addToMusicCategoryList(currentUser.getId(), MusicCategoryMapper.INSTANCE.createMusicCategoryRequestDtoToMusicCategory(createMusicCategoryRequestDto));
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.CREATED);
    }

    @PatchMapping("/musics/categories/{musicCategoryId}")
    @Operation(summary = "Remove music category from user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> removeFromMusicCategoryList(@PathVariable String musicCategoryId){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.removeFromMusicCategoryList(currentUser.getId(), musicCategoryId);
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.NO_CONTENT);
    }

    //Movie
    @PatchMapping("/movies")
    @Operation(summary = "Add movie to user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> addToMovieList(@RequestBody CreateMovieRequestDto createMovieRequestDto){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.addToMovieList(currentUser.getId(), MovieMapper.INSTANCE.createMovieRequestDtoToMovie(createMovieRequestDto));
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.CREATED);
    }

    @PatchMapping("/movies/{movieId}")
    @Operation(summary = "Remove movie from user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> removeFromMovieList(@PathVariable String movieId){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.removeFromMovieList(currentUser.getId(), movieId);
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.NO_CONTENT);
    }

    //Movie category
    @PatchMapping("/movies/categories")
    @Operation(summary = "Add movie category to user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> addToMovieCategoryList(@RequestBody CreateMovieCategoryRequestDto createMovieCategoryRequestDto){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.addToMovieCategoryList(currentUser.getId(), MovieCategoryMapper.INSTANCE.createMovieCategoryRequestDtoToMusicCategory(createMovieCategoryRequestDto));
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.CREATED);
    }

    @PatchMapping("/movies/categories/{movieCategoryId}")
    @Operation(summary = "Remove movie category from user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> removeFromMovieCategoryList(@PathVariable String movieCategoryId){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.removeFromMovieCategoryList(currentUser.getId(), movieCategoryId);
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.NO_CONTENT);
    }

    //Tv series
    @PatchMapping("/tvSeries")
    @Operation(summary = "Add tv series to user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> addToTvSeriesList(@RequestBody CreateTvSeriesRequestDto createTvSeriesRequestDto){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.addToTvSeriesList(currentUser.getId(), TvSeriesMapper.INSTANCE.createTvSeriesRequestDtoToTvSeries(createTvSeriesDto));
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.CREATED);
    }

    @PatchMapping("/tvSeries/{tvSeriesId}")
    @Operation(summary = "Remove tv series from user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> removeFromTvSeriesList(@PathVariable String tvSeriesId){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.removeFromTvSeriesList(currentUser.getId(), tvSeriesId);
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.NO_CONTENT);
    }

    //Tv series category
    @PatchMapping("/tvSeries/categories")
    @Operation(summary = "Add tv series category to user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> addToTvSeriesCategoryList(@RequestBody CreateTvSeriesCategoryRequestDto createTvSeriesCategoryRequestDto){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.addToTvSeriesCategoryList(currentUser.getId(), TvSeriesCategoryMapper.INSTANCE.createTvSeriesCategoryRequestDtoToTvSeriesCategory(createTvSeriesCategoryRequestDto));
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.CREATED);
    }

    @PatchMapping("/tvSeries/categories/{tvSeriesCategoryId}")
    @Operation(summary = "Remove tv series category from user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> removeFromTvSeriesCategoryList(@PathVariable String tvSeriesCategoryId){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.removeFromTvSeriesCategoryList(currentUser.getId(), tvSeriesCategoryId);
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.NO_CONTENT);
    }

    //Hobby
    @PatchMapping("/hobbies")
    @Operation(summary = "Add tv series to user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> addToHobbyList(@RequestBody CreateHobbyRequestDto createHobbyRequestDto){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.addToHobbyList(currentUser.getId(), HobbyMapper.INSTANCE.createHobbyRequestDtoToHobby(createHobbyRequestDto));
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.CREATED);
    }

    @PatchMapping("/hobbies/{hobbiesId}")
    @Operation(summary = "Remove tv series from user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> removeFromHobbyList(@PathVariable String hobbiesId){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.removeFromHobbyList(currentUser.getId(), hobbiesId);
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.NO_CONTENT);
    }
}
