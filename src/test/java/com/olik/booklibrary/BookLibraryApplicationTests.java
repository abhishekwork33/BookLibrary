package com.olik.booklibrary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olik.booklibrary.author.dto.AuthorDto;
import com.olik.booklibrary.author.service.AuthorService;
import com.olik.booklibrary.book.dto.BookDto;
import com.olik.booklibrary.book.service.BookService;
import com.olik.booklibrary.error.BookAlreadyRentedException;
import com.olik.booklibrary.rental.dto.RentalDto;
import com.olik.booklibrary.rental.service.RentalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class BookLibraryApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @Mock
    private BookService bookService;

    @Mock
    private RentalService rentalService;


    @Test
    public void testGetAllAuthors() throws Exception {
        AuthorDto author1 = new AuthorDto(1L, "Chetan Bhagat", "Biography Chetan");
        AuthorDto author2 = new AuthorDto(2L, "Anita Nair", "Biography Anita");
        when(authorService.findAllAuthors()).thenReturn(Arrays.asList(author1, author2));

        mockMvc.perform(MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Chetan Bhagat"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Anita Nair"));
    }

    @Test
    public void testGetAllBooks() throws Exception {
        BookDto book1 = BookDto.builder().id(1L).title("Book A").available(true).publicationYear(new Date()).build();
        BookDto book2 = BookDto.builder().id(2L).title("Book B").available(false).publicationYear(new Date()).build();
        when(bookService.findAllBooks()).thenReturn(Arrays.asList(book1, book2));

        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Book A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Book B"));
    }

    @Test
    public void testGetAllRentals() throws Exception {
        // Mocking data
        RentalDto rental1 = RentalDto.builder()
                .rentalDate(LocalDateTime.now())
                .returnDate(null)
                .renterName("Abhishek Singh")
                .build();
        RentalDto rental2 = RentalDto.builder()
                .rentalDate(LocalDateTime.now())
                .returnDate(LocalDateTime.now().plusDays(7))
                .renterName("Mohit Nair")
                .build();
        when(rentalService.getAllRentals()).thenReturn(Arrays.asList(rental1, rental2));

        mockMvc.perform(MockMvcRequestBuilders.get("/rentals")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].renterName").value("Abhishek Singh"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].renterName").value("Mohit Nair"));
    }


    @Test
    public void testCreateRental_Successful() throws Exception {
        // Mocking data
        RentalDto rentalDto = RentalDto.builder()
                .rentalDate(LocalDateTime.now())
                .returnDate(null)
                .renterName("John Doe")
                .build();
        when(rentalService.createRental(any(), any())).thenReturn(rentalDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/rentals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(rentalDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.renterName").value("John Doe"));
    }

    @Test
    public void testCreateRental_BookAlreadyRented() throws Exception {
        RentalDto rentalDto = RentalDto.builder()
                .rentalDate(LocalDateTime.now())
                .returnDate(null)
                .renterName("ME")
                .build();
        when(rentalService.createRental(any(), any())).thenThrow(new BookAlreadyRentedException("Book already rented"));

        mockMvc.perform(MockMvcRequestBuilders.post("/rentals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(rentalDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testCreateAuthor() throws Exception {
        AuthorDto authorDto = new AuthorDto(null, "Author A", "Biography A");
        AuthorDto savedAuthor = new AuthorDto(1L, "Author B", "Biography B");
        when(authorService.createNewAuthor(any())).thenReturn(savedAuthor);

        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(authorDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Author A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.biography").value("Biography B"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        BookDto bookDto = BookDto.builder().id(1L).title("Updated Book").available(true).publicationYear(new Date()).build();
        BookDto updatedBook = BookDto.builder().id(1L).title("Updated Book").available(true).publicationYear(new Date()).build();
        Long authorId = 1L;
        when(bookService.updateBook(eq(1L), any(), eq(authorId))).thenReturn(updatedBook);

        mockMvc.perform(MockMvcRequestBuilders.patch("/books/1")
                        .param("authorId", "1") // Pass the author ID as a request parameter
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated Book"));
    }


    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

