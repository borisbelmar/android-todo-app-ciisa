package cl.ciisa.todoapp.models;

public class TaskMapper {
    private ITask task;

    public TaskMapper(ITask task) {
        this.task = task;
    }

    public Task toBase() {
        Task baseTask = new Task(
                this.task.getTitle(),
                this.task.getDescription(),
                this.task.getDue(),
                this.task.getUserId()
        );
        baseTask.setId(this.task.getId());
        return baseTask;
    }

    public TaskEntity toEntity() {
        return new TaskEntity(
                this.task.getId(),
                this.task.getTitle(),
                this.task.getDescription(),
                this.task.getDue(),
                this.task.getUserId()
        );
    }
}
