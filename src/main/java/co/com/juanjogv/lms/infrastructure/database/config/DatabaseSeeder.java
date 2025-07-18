package co.com.juanjogv.lms.infrastructure.database.config;

import co.com.juanjogv.lms.domain.model.Author;
import co.com.juanjogv.lms.domain.model.Book;
import co.com.juanjogv.lms.domain.model.BookAvailability;
import co.com.juanjogv.lms.domain.model.BorrowingRecord;
import co.com.juanjogv.lms.domain.model.BorrowingRecordKey;
import co.com.juanjogv.lms.domain.model.Role;
import co.com.juanjogv.lms.domain.model.User;
import co.com.juanjogv.lms.domain.repository.AuthorRepository;
import co.com.juanjogv.lms.domain.repository.BookRepository;
import co.com.juanjogv.lms.domain.repository.BorrowingRecordRepository;
import co.com.juanjogv.lms.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static co.com.juanjogv.lms.domain.model.BookAvailability.AVAILABLE;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void run(String... args) {
        log.info("Iniciando poblado de la base de datos...");

        // Verificar si ya existen datos
        if (authorRepository.count() > 0) {
            log.info("La base de datos ya contiene datos. Omitiendo el poblado inicial.");
            return;
        }

        seedUsers();
        seedAuthors();
        seedBooks();
        seedBorrowingRecords();

        log.info("Base de datos poblada exitosamente.");
    }

    private void seedUsers() {
        log.info("Creando usuarios...");

        List<User> users = List.of(
                // Administrador
                User.builder()
                        .name("Admin Sistema")
                        .email("admin@biblioteca.com")
                        .password(passwordEncoder.encode("admin123"))
                        .role(Role.ADMIN)
                        .borrowingRecord(new ArrayList<>())
                        .build(),

                // Bibliotecarios
                User.builder()
                        .name("María González")
                        .email("maria.gonzalez@biblioteca.com")
                        .password(passwordEncoder.encode("biblio123"))
                        .role(Role.LIBRARIAN)
                        .borrowingRecord(new ArrayList<>())
                        .build(),

                User.builder()
                        .name("Carlos Rodríguez")
                        .email("carlos.rodriguez@biblioteca.com")
                        .password(passwordEncoder.encode("biblio123"))
                        .role(Role.LIBRARIAN)
                        .borrowingRecord(new ArrayList<>())
                        .build(),

                // Usuarios regulares
                User.builder()
                        .name("Ana Martínez")
                        .email("ana.martinez@email.com")
                        .password(passwordEncoder.encode("user123"))
                        .role(Role.USER)
                        .borrowingRecord(new ArrayList<>())
                        .build(),

                User.builder()
                        .name("Luis Hernández")
                        .email("luis.hernandez@email.com")
                        .password(passwordEncoder.encode("user123"))
                        .role(Role.USER)
                        .borrowingRecord(new ArrayList<>())
                        .build(),

                User.builder()
                        .name("Carmen Jiménez")
                        .email("carmen.jimenez@email.com")
                        .password(passwordEncoder.encode("user123"))
                        .role(Role.USER)
                        .borrowingRecord(new ArrayList<>())
                        .build(),

                User.builder()
                        .name("Roberto Silva")
                        .email("roberto.silva@email.com")
                        .password(passwordEncoder.encode("user123"))
                        .role(Role.USER)
                        .borrowingRecord(new ArrayList<>())
                        .build(),

                User.builder()
                        .name("Elena Morales")
                        .email("elena.morales@email.com")
                        .password(passwordEncoder.encode("user123"))
                        .role(Role.USER)
                        .borrowingRecord(new ArrayList<>())
                        .build(),

                User.builder()
                        .name("Diego Vargas")
                        .email("diego.vargas@email.com")
                        .password(passwordEncoder.encode("user123"))
                        .role(Role.USER)
                        .borrowingRecord(new ArrayList<>())
                        .build(),

                User.builder()
                        .name("Sofía Castro")
                        .email("sofia.castro@email.com")
                        .password(passwordEncoder.encode("user123"))
                        .role(Role.USER)
                        .borrowingRecord(new ArrayList<>())
                        .build()
        );

        userRepository.saveAll(users);
        log.info("Creados {} usuarios", users.size());
    }

    private void seedAuthors() {
        log.info("Creando autores...");

        List<Author> authors = List.of(
                Author.builder()
                        .name("Gabriel García Márquez")
                        .books(new ArrayList<>())
                        .build(),
                Author.builder()
                        .name("Isabel Allende")
                        .books(new ArrayList<>())
                        .build(),
                Author.builder()
                        .name("Mario Vargas Llosa")
                        .books(new ArrayList<>())
                        .build(),
                Author.builder()
                        .name("Jorge Luis Borges")
                        .books(new ArrayList<>())
                        .build(),
                Author.builder()
                        .name("Octavio Paz")
                        .books(new ArrayList<>())
                        .build(),
                Author.builder()
                        .name("Paulo Coelho")
                        .books(new ArrayList<>())
                        .build(),
                Author.builder()
                        .name("Laura Esquivel")
                        .books(new ArrayList<>())
                        .build(),
                Author.builder()
                        .name("Roberto Bolaño")
                        .books(new ArrayList<>())
                        .build(),
                Author.builder()
                        .name("Julio Cortázar")
                        .books(new ArrayList<>())
                        .build(),
                Author.builder()
                        .name("Carlos Fuentes")
                        .books(new ArrayList<>())
                        .build()
        );

        authorRepository.saveAll(authors);
        log.info("Creados {} autores", authors.size());
    }

    private void seedBooks() {
        log.info("Creando libros...");

        List<Author> authors = authorRepository.findAll();

        // Gabriel García Márquez
        Author garciaMarquez = authors.stream()
                .filter(a -> a.getName().equals("Gabriel García Márquez"))
                .findFirst().orElseThrow();

        List<Book> books = new ArrayList<>(List.of(
                Book.builder()
                        .title("Cien años de soledad")
                        .isbn("978-84-376-0494-7")
                        .availability(AVAILABLE)
                        .author(garciaMarquez)
                        .borrowingRecord(new ArrayList<>())
                        .build(),
                Book.builder()
                        .title("El amor en los tiempos del cólera")
                        .isbn("978-84-376-0495-4")
                        .availability(AVAILABLE)
                        .author(garciaMarquez)
                        .borrowingRecord(new ArrayList<>())
                        .build(),
                Book.builder()
                        .title("Crónica de una muerte anunciada")
                        .isbn("978-84-376-0496-1")
                        .availability(BookAvailability.BORROWED)
                        .author(garciaMarquez)
                        .borrowingRecord(new ArrayList<>())
                        .build()
        ));

        // Isabel Allende
        Author isabelAllende = authors.stream()
                .filter(a -> a.getName().equals("Isabel Allende"))
                .findFirst().orElseThrow();

        books.addAll(List.of(
                Book.builder()
                        .title("La casa de los espíritus")
                        .isbn("978-84-9759-157-3")
                        .availability(AVAILABLE)
                        .author(isabelAllende)
                        .borrowingRecord(new ArrayList<>())
                        .build(),
                Book.builder()
                        .title("Paula")
                        .isbn("978-84-9759-158-0")
                        .availability(AVAILABLE)
                        .author(isabelAllende)
                        .borrowingRecord(new ArrayList<>())
                        .build()
        ));

        // Mario Vargas Llosa
        Author vargasLlosa = authors.stream()
                .filter(a -> a.getName().equals("Mario Vargas Llosa"))
                .findFirst().orElseThrow();

        books.addAll(List.of(
                Book.builder()
                        .title("La ciudad y los perros")
                        .isbn("978-84-663-2157-9")
                        .availability(AVAILABLE)
                        .author(vargasLlosa)
                        .borrowingRecord(new ArrayList<>())
                        .build(),
                Book.builder()
                        .title("Conversación en La Catedral")
                        .isbn("978-84-663-2158-6")
                        .availability(BookAvailability.BORROWED)
                        .author(vargasLlosa)
                        .borrowingRecord(new ArrayList<>())
                        .build()
        ));

        // Jorge Luis Borges
        Author borges = authors.stream()
                .filter(a -> a.getName().equals("Jorge Luis Borges"))
                .findFirst().orElseThrow();

        books.addAll(List.of(
                Book.builder()
                        .title("Ficciones")
                        .isbn("978-84-376-0789-4")
                        .availability(AVAILABLE)
                        .author(borges)
                        .borrowingRecord(new ArrayList<>())
                        .build(),
                Book.builder()
                        .title("El Aleph")
                        .isbn("978-84-376-0790-0")
                        .availability(AVAILABLE)
                        .author(borges)
                        .borrowingRecord(new ArrayList<>())
                        .build()
        ));

        // Octavio Paz
        Author octavioPaz = authors.stream()
                .filter(a -> a.getName().equals("Octavio Paz"))
                .findFirst().orElseThrow();

        books.add(Book.builder()
                .title("El laberinto de la soledad")
                .isbn("978-84-376-0791-7")
                .availability(AVAILABLE)
                .author(octavioPaz)
                .borrowingRecord(new ArrayList<>())
                .build());

        // Paulo Coelho
        Author pauloCoelho = authors.stream()
                .filter(a -> a.getName().equals("Paulo Coelho"))
                .findFirst().orElseThrow();

        books.add(Book.builder()
                .title("El Alquimista")
                .isbn("978-84-08-04479-1")
                .availability(AVAILABLE)
                .author(pauloCoelho)
                .borrowingRecord(new ArrayList<>())
                .build());

        // Laura Esquivel
        Author lauraEsquivel = authors.stream()
                .filter(a -> a.getName().equals("Laura Esquivel"))
                .findFirst().orElseThrow();

        books.add(Book.builder()
                .title("Como agua para chocolate")
                .isbn("978-84-376-0792-4")
                .availability(BookAvailability.BORROWED)
                .author(lauraEsquivel)
                .borrowingRecord(new ArrayList<>())
                .build());

        // Roberto Bolaño
        Author bolano = authors.stream()
                .filter(a -> a.getName().equals("Roberto Bolaño"))
                .findFirst().orElseThrow();

        books.add(Book.builder()
                .title("Los detectives salvajes")
                .isbn("978-84-339-6756-7")
                .availability(AVAILABLE)
                .author(bolano)
                .borrowingRecord(new ArrayList<>())
                .build());

        // Julio Cortázar
        Author cortazar = authors.stream()
                .filter(a -> a.getName().equals("Julio Cortázar"))
                .findFirst().orElseThrow();

        books.addAll(List.of(
                Book.builder()
                        .title("Rayuela")
                        .isbn("978-84-376-0793-1")
                        .availability(AVAILABLE)
                        .author(cortazar)
                        .borrowingRecord(new ArrayList<>())
                        .build(),
                Book.builder()
                        .title("Bestiario")
                        .isbn("978-84-376-0794-8")
                        .availability(AVAILABLE)
                        .author(cortazar)
                        .borrowingRecord(new ArrayList<>())
                        .build()
        ));

        // Carlos Fuentes
        Author carlosFuentes = authors.stream()
                .filter(a -> a.getName().equals("Carlos Fuentes"))
                .findFirst().orElseThrow();

        books.add(Book.builder()
                .title("La muerte de Artemio Cruz")
                .isbn("978-84-376-0795-5")
                .availability(AVAILABLE)
                .author(carlosFuentes)
                .borrowingRecord(new ArrayList<>())
                .build());

        bookRepository.saveAll(books);
        log.info("Creados {} libros", books.size());
    }

    private void seedBorrowingRecords() {
        log.info("Creando registros de préstamo...");

        List<Book> borrowedBooks = bookRepository.findByAvailability(BookAvailability.BORROWED);
        List<User> regularUsers = userRepository.findByRole(Role.USER);
        List<BorrowingRecord> borrowingRecords = new ArrayList<>();

        // Crear registros de préstamo activos (libros prestados)
        for (int i = 0; i < borrowedBooks.size(); i++) {
            Book book = borrowedBooks.get(i);
            User borrower = regularUsers.get(i % regularUsers.size());

            OffsetDateTime borrowDate = OffsetDateTime.now().minusDays(i + 1);
            OffsetDateTime dueDate = borrowDate.plusDays(14); // 14 días de préstamo

            BorrowingRecord record = BorrowingRecord.builder()
                    .id(new BorrowingRecordKey(book.getId(), borrower.getId()))
                    .book(book)
                    .user(borrower)
                    .borrowDate(borrowDate)
                    .dueDate(dueDate)
                    .returnedDate(null) // No devuelto aún
                    .build();

            borrowingRecords.add(record);
        }

        // Crear algunos registros históricos (libros devueltos)
        List<Book> availableBooks = bookRepository.findByAvailability(AVAILABLE);
        for (int i = 0; i < Math.min(5, availableBooks.size()); i++) {
            Book book = availableBooks.get(i);
            User borrower = regularUsers.get(i % regularUsers.size());

            OffsetDateTime borrowDate = OffsetDateTime.now().minusDays(20 + i);
            OffsetDateTime dueDate = borrowDate.plusDays(14);
            OffsetDateTime returnDate = OffsetDateTime.now().minusDays(5 + i);

            BorrowingRecord historicalRecord = BorrowingRecord.builder()
                    .id(new BorrowingRecordKey(book.getId(), borrower.getId()))
                    .book(book)
                    .user(borrower)
                    .borrowDate(borrowDate)
                    .dueDate(dueDate)
                    .returnedDate(returnDate)
                    .build();

            borrowingRecords.add(historicalRecord);
        }

        // Crear algunos registros vencidos (libros prestados pero no devueltos a tiempo)
        for (int i = 0; i < Math.min(2, regularUsers.size()); i++) {
            Book book = availableBooks.get(i + 5); // Usar libros diferentes
            User borrower = regularUsers.get(i);

            OffsetDateTime borrowDate = OffsetDateTime.now().minusDays(30 + i);
            OffsetDateTime dueDate = borrowDate.plusDays(14);
            OffsetDateTime returnDate = OffsetDateTime.now().minusDays(3 + i);

            BorrowingRecord overdueRecord = BorrowingRecord.builder()
                    .id(new BorrowingRecordKey(book.getId(), borrower.getId()))
                    .book(book)
                    .user(borrower)
                    .borrowDate(borrowDate)
                    .dueDate(dueDate)
                    .returnedDate(returnDate) // Devuelto tarde
                    .build();

            borrowingRecords.add(overdueRecord);
        }

        borrowingRecordRepository.saveAll(borrowingRecords);
        log.info("Creados {} registros de préstamo", borrowingRecords.size());
    }
}
