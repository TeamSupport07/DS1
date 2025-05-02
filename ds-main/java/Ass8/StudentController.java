
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final Map<Integer, String> studentDB = new HashMap<>();

    @GetMapping("/add")
    public String addStudent(@RequestParam int id, @RequestParam String name) {
        studentDB.put(id, name);
        return "Student added: " + name + " (ID: " + id + ")";
    }

    @GetMapping("/get")
    public String getStudent(@RequestParam int id) {
        String name = studentDB.get(id);
        if (name != null) {
            return "Student ID: " + id + ", Name: " + name;
        } else {
            return "Student not found with ID: " + id;
        }
    }

    @GetMapping("/all")
    public Map<Integer, String> getAllStudents() {
        return studentDB;
    }
}
