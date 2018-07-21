import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Expand;

public class Expander extends Expand {
    public Expander(String taskType, String taskName){
        this.setProject(new Project());
        this.getProject().init();
        this.setOwningTarget(new Target());
        this.setTaskType(taskType);
        this.setTaskName(taskName);
    }
}
