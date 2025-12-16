import java.util.ArrayList;
import java.util.List;

public class SimpleMultiChildTreeNode<T> {
    public T value;
    public List<SimpleMultiChildTreeNode<T>> children;

    public SimpleMultiChildTreeNode(T value) {
        this.value = value;
    }

    public SimpleMultiChildTreeNode<T> addAsChild(T value) {
        if (children == null) children = new ArrayList<>();
        SimpleMultiChildTreeNode<T> child = new SimpleMultiChildTreeNode<>(value);
        children.add(child);
        return child;
    }
}