package org.zerock.b01.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.repository.BoardRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor //(final로 설정해둔것에 의존성 주입 -> 생성자로 주입했음)
@Transactional  //만약에 내가 작업을하는데 하나가 아니라 두개를 작업해야하는경우
//translactioal 은 디비에 여러 작업을 해야하는경우 , 완전 성공시 처리 한다.
public class BoardServiceImpl implements BoardService{

    //@requir 생성자 주입을 받으려면 final로 설정을 해야한다.
    private final ModelMapper modelMapper;  //requir때문에 final로 설정을하고 생성자 주입을 받음
    private final BoardRepository boardRepository; ////requir때문에 final로 설정을하고 생성자 주입을 받음

    @Override
    public Long register(BoardDTO boardDTO) {
        Board board = modelMapper.map(boardDTO, Board.class);

        Long bno = boardRepository.save(board).getBno();
        return bno;
    }

    @Override
    public BoardDTO readOne(Long bno) {
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow(); //없으면 nosearch 익셉션 발생

        BoardDTO boardDTO = modelMapper.map(board,BoardDTO.class);
        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        //Optional null
        Optional<Board> result = boardRepository.findById(boardDTO.getBno());

        //불러오지 못하면 없는 얘를 수정한다는거니까 없는 데이터를 수정하는건 불가능 예외처리 를 하기위함
        Board board = result.orElseThrow();

        //보드 레포지토리에 체인지는 두개만 가능했다 아티르과 콘텐츠  이 두가지만 ㅂ수정한다.
        //보드 체인지로 불러와서 수정하면
        //보드에서 불러오면 동작을하면 퍼스턴트 콘텍스트에 먼저 저장을한다 객체정보가 남음
        //
        board.change(boardDTO.getTitle(),boardDTO.getContent());
        //위에 불러온 bno가 같은 얘를 수정함
        boardRepository.save(board);
    }

    @Override
    public void remove(Long bno) {

        boardRepository.deleteById(bno);
    }

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable =pageRequestDTO.getPageble("bno");
        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);
        result.getContent().forEach(i -> log.info("Service에서 search 테스트 : "+i));

        //변환..Board -> BoardDTO
        List<BoardDTO> dtoList = result.getContent().stream()
                .map(board -> modelMapper.map(board,BoardDTO.class))
                .collect(Collectors.toList());

        //보드디티오라는 제너릭타입으로 만들겠다
        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }



}
