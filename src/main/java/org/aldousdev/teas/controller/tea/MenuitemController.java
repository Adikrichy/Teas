package org.aldousdev.teas.controller.tea;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.aldousdev.teas.dto.request.MenuitemDto;
import org.aldousdev.teas.models.tea.Menuitem;
import org.aldousdev.teas.service.storage.StorageService;
import org.aldousdev.teas.service.tea.menuitem.MenuitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MenuitemController {

    private final MenuitemService menuitemService;
    private final StorageService storageService;

    @Autowired
    MenuitemController(MenuitemService menuitemService, StorageService storageService) {
        this.menuitemService = menuitemService;
        this.storageService = storageService;
    }
//read
    @GetMapping("/menuitems")
    public List<Menuitem> getMenuitems() {
        return menuitemService.getMenuitems();

    }

    @GetMapping("/category/{id}/menuitems")
    public List<Menuitem> getMenuitemsByCategory(@PathVariable Long id) {
        return menuitemService.getMenuitemsByCategoryId(id);
    }

    // ADMIN ONLY
    //Create
    @PostMapping("/menuitem")
    public ResponseEntity<Menuitem> createMenuitem(@RequestBody MenuitemDto menuitemDto) {
        Menuitem menuitem = menuitemService.createMenuitem(menuitemDto);
        return new ResponseEntity<>(menuitem, HttpStatus.CREATED);
    }
//Update
    @PatchMapping("/menuitem/{id}")
    public ResponseEntity<Menuitem> updateMenuitem(@PathVariable Long id, @RequestBody Map<String, Object> menuitemDto) {
        Menuitem menuitem = menuitemService.updateMenuitem(id, menuitemDto);
        return new ResponseEntity<>(menuitem, HttpStatus.OK);
    }
//delete
    @DeleteMapping("/menuitem/{id}")
    public ResponseEntity<String> deleteMenuitem(@PathVariable Long id) {
        menuitemService.deleteMenuitem(id);
        return new ResponseEntity<>("Menuitem deleted", HttpStatus.NO_CONTENT);
    }

//    @PostMapping("/menuitem/{id}/img")
//    public ResponseEntity<?> updateMenuitemImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
//
//            storageService.store(file);
//            Map<String, Object> menuitemDto = new HashMap<>();
//            menuitemDto.put("imageUrl", file.getOriginalFilename());
//            Menuitem menuitem = menuitemService.updateMenuitem(id, menuitemDto);
//            return new ResponseEntity<>(menuitem, HttpStatus.OK);
//
//    }

    @PostMapping(
            value = "/menuitem/{id}/img",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(
            summary = "Обновить изображение пункта меню",
            description = "Загружает или обновляет изображение для существующего пункта меню по его ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Изображение успешно обновлено, возвращает обновленный пункт меню",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Menuitem.class))
            ),
            @ApiResponse(responseCode = "400", description = "Файл не прикреплён или пуст", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пункт меню не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    public ResponseEntity<?> updateMenuitemImage(
            @PathVariable Long id,
            @RequestPart("file") MultipartFile file
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Файл не прикреплён или пуст");
        }

        try {
            storageService.store(file);

            Map<String, Object> menuitemDto = new HashMap<>();
            menuitemDto.put("imageUrl", file.getOriginalFilename());

            Menuitem updatedMenuitem = menuitemService.updateMenuitem(id, menuitemDto);
            return ResponseEntity.ok(updatedMenuitem);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пункт меню не найден");
        }
    }


    @DeleteMapping("/menuitem/img/{id}")
    public ResponseEntity<?> deleteMenuitemImage(@PathVariable Long id) {
//            research hoe to delete backend or data
//            storageService.store(file);
            Map<String, Object> menuitemDto = new HashMap<>();
            menuitemDto.put("imageUrl", "");
            Menuitem menuitem = menuitemService.updateMenuitem(id, menuitemDto);
            return new ResponseEntity<>(menuitem, HttpStatus.OK);


    }

    @PatchMapping("/menuitem/toggleActive/{id}")
    public ResponseEntity<?> toggleActiveMenuitem(@PathVariable Long id) {
            menuitemService.toggleActiveMenuitem(id);
            return new ResponseEntity<>(HttpStatus.OK);

    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " +e.getMessage());
    }
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}



//import io.swagger.v3.oas.annotations.Operation; // Для описания операции
//import io.swagger.v3.oas.annotations.Parameter; // Для описания параметров
//import io.swagger.v3.oas.annotations.media.Content; // Для указания типа контента параметра/ответа
//import io.swagger.v3.oas.annotations.media.Schema; // Для описания схемы данных (параметра или ответа)
//import io.swagger.v3.oas.annotations.responses.ApiResponse; // Для описания одного ответа
//import io.swagger.v3.oas.annotations.responses.ApiResponses; // Для группы ответов
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType; // Для MediaType.MULTIPART_FORM_DATA_VALUE
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//        import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException; // Предполагаем, что store может бросить IOException
//import java.util.HashMap;
//import java.util.Map;
//
//// Предположим, что у тебя есть классы Menuitem, MenuitemService, StorageService
//// import com.yourpackage.Menuitem;
//// import com.yourpackage.service.MenuitemService;
//// import com.yourpackage.service.StorageService;
//
//@RestController
//// @RequestMapping("/api") // Хорошая практика - добавить базовый путь для API
//public class MenuitemImageController { // Дал классу более осмысленное имя
//
//    // Заглушки для сервисов, замени на свои реальные бины
//    private final StorageService storageService = null; // = ...; // @Autowired
//    private final MenuitemService menuitemService = null; // = ...; // @Autowired
//
//    // --- НАЧАЛО ИЗМЕНЕНИЙ ---
//
//    @PostMapping(
//            value = "/menuitem/{id}/img", // Путь остался прежним
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE // <-- ДОБАВЛЕНО: Явно указываем Swagger (и Spring), что этот метод принимает multipart/form-data.
//            // Это КЛЮЧЕВОЙ момент для отображения поля загрузки файла в Swagger UI.
//    )
//    @Operation( // <-- ДОБАВЛЕНО: Основная аннотация для описания эндпоинта в Swagger.
//            summary = "Обновить изображение пункта меню", // Краткое описание, видимое в списке операций.
//            description = "Загружает или обновляет изображение для существующего пункта меню по его ID." // Более подробное описание.
//    )
//    @ApiResponses(value = { // <-- ДОБАВЛЕНО: Описываем возможные HTTP-ответы от эндпоинта.
//            @ApiResponse(
//                    responseCode = "200", // Код успешного ответа
//                    description = "Изображение успешно обновлено, возвращает обновленный пункт меню",
//                    content = { @Content(mediaType = "application/json", // Указываем тип контента ответа
//                            schema = @Schema(implementation = Menuitem.class)) } // Указываем, что в теле ответа будет объект Menuitem
//            ),
//            @ApiResponse(
//                    responseCode = "400", // Код ошибки "Плохой запрос"
//                    description = "Некорректный запрос (например, файл не прикреплен или пустой)",
//                    content = @Content // Ответ без тела или с простым текстом
//            ),
//            @ApiResponse(
//                    responseCode = "404", // Код ошибки "Не найдено"
//                    description = "Пункт меню с указанным ID не найден",
//                    content = @Content
//            ),
//            @ApiResponse(
//                    responseCode = "500", // Код ошибки "Внутренняя ошибка сервера"
//                    description = "Ошибка при сохранении файла или другая серверная проблема",
//                    content = @Content
//            )
//    })
//    public ResponseEntity<?> updateMenuitemImage(
//
//            @Parameter( // <-- ДОБАВЛЕНО: Описываем параметр пути 'id'.
//                    name = "id",
//                    description = "ID пункта меню, для которого обновляется изображение",
//                    required = true, // Указываем, что параметр обязателен
//                    example = "123"  // Пример значения
//            )
//            @PathVariable Long id,
//
//            @Parameter( // <-- ДОБАВЛЕНО: Описываем параметр 'file'.
//                    name = "file",
//                    description = "Файл изображения для загрузки (multipart/form-data)",
//                    required = true, // Указываем, что файл обязателен
//                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE) // Дополнительно подчеркиваем, что это часть multipart запроса.
//                    // Springdoc часто сам это понимает из-за типа MultipartFile и consumes в @PostMapping.
//            )
//            @RequestParam("file") MultipartFile file // Параметр для получения файла, имя "file" должно совпадать с ключом в multipart/form-data запросе.
//    ) {
//        // Добавим базовую обработку ошибок для соответствия @ApiResponses
//        try {
//            // Проверка, что файл не пустой (хорошая практика)
//            if (file == null || file.isEmpty()) {
//                return ResponseEntity.badRequest().body("Файл не предоставлен или пуст."); // 400 Bad Request
//            }
//
//            // Логика сохранения файла
//            storageService.store(file); // Предполагаем, что этот метод может выбросить исключение (например, IOException)
//
//            // Подготовка данных для обновления
//            Map<String, Object> menuitemDto = new HashMap<>();
//            menuitemDto.put("imageUrl", file.getOriginalFilename()); // Используем имя файла как URL (убедись, что это твоя логика)
//
//            // Обновление пункта меню
//            // Предполагаем, что updateMenuitem может вернуть null или бросить исключение, если id не найден
//            Menuitem menuitem = menuitemService.updateMenuitem(id, menuitemDto);
//
//            if (menuitem == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body("Пункт меню с ID " + id + " не найден."); // 404 Not Found
//            }
//
//            // Возвращаем успешный ответ с обновленным объектом
//            return new ResponseEntity<>(menuitem, HttpStatus.OK); // 200 OK
//
//        } catch (IOException e) { // Пример обработки ошибки сохранения файла
//            // Здесь хорошо бы добавить логирование ошибки
//            // log.error("Ошибка сохранения файла для menuitem {}", id, e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Ошибка сервера при сохранении файла: " + e.getMessage()); // 500 Internal Server Error
//        } catch (Exception e) { // Общая обработка других ошибок
//            // Здесь хорошо бы добавить логирование ошибки
//            // log.error("Неожиданная ошибка при обновлении изображения для menuitem {}", id, e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Внутренняя ошибка сервера: " + e.getMessage()); // 500 Internal Server Error
//        }
//    }
//    // --- КОНЕЦ ИЗМЕНЕНИЙ ---
//
//    // Заглушки для классов (замени на свои)
//    interface StorageService { void store(MultipartFile file) throws IOException; }
//    interface MenuitemService { Menuitem updateMenuitem(Long id, Map<String, Object> dto); }
//    // @Schema // Если Menuitem это твой класс, можно добавить Schema для лучшего описания в Swagger
//    static class Menuitem {
//        public Long id;
//        public String name;
//        public String imageUrl;
//        // ... другие поля
//    }
//}
