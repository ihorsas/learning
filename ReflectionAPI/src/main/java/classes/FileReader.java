public class FileReader {
  private String path;

  public FileReader() {
    this.path = "";
  }

  public FileReader(String path) {
    this.path = path;
  }

  public String read() {
    System.out.println("Reading file");
    if (!path.isEmpty()) {
      return path + " result";
    }
    return path;
  }

  public void updatePath(String path) {
    this.path = path;
  }
}
