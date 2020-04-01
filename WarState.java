public abstract class WarState {
  protected static WarContext context;
  protected WarState() {
    //context = LibContext.instance();
  }
  public abstract void run();
}
