package pl.edu.agh.to2.lets_go_fishing.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class FileHandlerServiceTest {

    private FileHandlerService service;

    @BeforeEach
    void setUp() {
        service = new FileHandlerService();
    }

    @Test
    void readFile_shouldReadTxtFile() throws IOException {
        File mockFile = mock(File.class);
        when(mockFile.getName()).thenReturn("test.txt");
        when(mockFile.toPath()).thenReturn(new File("test.txt").toPath());
        try (MockedStatic<Files> filesMock = mockStatic(Files.class)) {
            filesMock.when(() -> Files.readAllBytes(any())).thenReturn("Sample text".getBytes());

            String content = service.readFile(mockFile);

            assertEquals("Sample text", content);
        }
    }

    @Test
    void readFile_shouldThrowExceptionForUnsupportedFileType() {
        File mockFile = mock(File.class);
        when(mockFile.getName()).thenReturn("test.unsupported");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.readFile(mockFile));
        assertEquals("Unsupported file type: test.unsupported", exception.getMessage());
    }
}
