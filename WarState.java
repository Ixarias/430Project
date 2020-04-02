public abstract class WarState {
  protected static WarContext context;
  protected WarState() {
    //context = WarContext.instance();
  }
  public abstract void run();
}
