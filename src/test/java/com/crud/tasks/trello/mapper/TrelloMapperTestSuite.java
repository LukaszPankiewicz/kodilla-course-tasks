package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    TrelloMapper trelloMapper;

    @Test
    void mapToBoardsTest() {
        //Given
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(new TrelloListDto("1", "List", true));
        trelloBoardDtos.add(new TrelloBoardDto("1","Board", trelloListDtos));

        //When
        List<TrelloBoard> testBoard = trelloMapper.mapToBoards(trelloBoardDtos);

        //Then
        assertEquals(trelloBoardDtos.get(0).getId(), testBoard.get(0).getId());
        assertEquals(trelloBoardDtos.get(0).getName(), testBoard.get(0).getName());
        assertEquals(trelloBoardDtos.get(0).getLists().size(), testBoard.get(0).getLists().size());
    }

    @Test
    void mapToBoardsDtoTest() {
        //Given
        TrelloListDto trelloList = new TrelloListDto("1", "Test List", false);
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(trelloList);
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("Test","1", trelloListDtoList);
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDto);
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtoList);

        //When
        String testTrelloId = trelloBoards.get(0).getId();
        String testTrelloName = trelloBoards.get(0).getName();
        List<TrelloList> testList = trelloBoards.get(0).getLists();

        //When
        assertEquals("1", testTrelloId);
        assertEquals("Test", testTrelloName);
        assertEquals(1, testList.size());

    }

    @Test
    void mapToListTest() {
        //Given
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(new TrelloListDto("1", "List1", true));

        //When
        List<TrelloList> testList = trelloMapper.mapToList(trelloListDtos);

        //Then
        assertEquals(trelloListDtos.get(0).getId(), testList.get(0).getId());
        assertEquals(trelloListDtos.get(0).getName(), testList.get(0).getName());
        assertEquals(trelloListDtos.get(0).isClosed(), testList.get(0).isClosed());

    }

    @Test
    public void MapToListDtoTest() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1", "List1", true));

        //When
        List<TrelloListDto> testList = trelloMapper.mapToListDto(trelloLists);

        //Then
        assertEquals(trelloLists.get(0).getId(), testList.get(0).getId());
        assertEquals(trelloLists.get(0).getName(), testList.get(0).getName());
        assertEquals(trelloLists.get(0).isClosed(), testList.get(0).isClosed());
    }

    @Test
    public void mapToCardTest() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card1", "Description", "Top", "1");

        //When
        TrelloCard testCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals(trelloCardDto.getName(), testCard.getName());
        assertEquals(trelloCardDto.getDescription(), testCard.getDescription());
        assertEquals(trelloCardDto.getPos(), testCard.getPos());
        assertEquals(trelloCardDto.getListId(), testCard.getListId());
    }

    @Test
    public void mapToCardDtoTest() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Card1", "Description", "Top", "1");

        //When
        TrelloCardDto testCard = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals(trelloCard.getName(), testCard.getName());
        assertEquals(trelloCard.getDescription(), testCard.getDescription());
        assertEquals(trelloCard.getPos(), testCard.getPos());
        assertEquals(trelloCard.getListId(), testCard.getListId());
    }



}
