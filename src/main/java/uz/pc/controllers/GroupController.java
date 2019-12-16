package uz.pc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pc.db.dao.interfaces.GroupDAO;
import uz.pc.db.entities.Group;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/groups")
@CrossOrigin("http://localhost:3000")
public class GroupController {

    private GroupDAO dao;

    public GroupController(GroupDAO dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<List<Group>> getAll() {
        return new ResponseEntity<>(dao.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<Group> get(@PathVariable int id) {
        if (id == -1)
            return new ResponseEntity<>(new Group(), HttpStatus.OK);
        return new ResponseEntity<>(dao.getById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public HttpStatus save(@Valid @RequestBody Group group) {
        dao.saveGroup(group);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Group>> delete(@PathVariable int id) {
        dao.deleteGroup(id);
        return new ResponseEntity<>(dao.getAll(), HttpStatus.OK);
    }

}
