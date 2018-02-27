package org.rachidcorp.controller;

import java.util.List;

import org.rachidcorp.dao.TaskRepository;
import org.rachidcorp.entity.Task;
import org.rachidcorp.exception.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Task> findTasks(){
		return taskRepository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{taskId}")
	public Task findTask(@PathVariable Long taskId) {
		validateTask(taskId);
		return taskRepository.findOne(taskId);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Task saveTask(@RequestBody Task t) {
		return taskRepository.save(t);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{taskId}")
	public Task updateTask(@RequestBody Task t, @PathVariable Long taskId) {
		validateTask(taskId);
		t.setId(taskId);
		return taskRepository.save(t);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{taskId}")
	public void deleteTask(@PathVariable Long taskId) {
		validateTask(taskId);
		taskRepository.delete(taskId);
	}
	
	private void validateTask(Long taskId) {
		Task t = taskRepository.findOne(taskId);
		if(t == null) throw new TaskNotFoundException(taskId);	
	}
	
}
