// spring-projects/spring-boot/blob/v1.3.1.RELEASE/spring-boot/src/main/java/org/springframework/boot/env/EnumerableCompositePropertySource.java
public class EnumerableCompositePropertySource
        extends EnumerablePropertySource<Collection<PropertySource<?>>> {

    private volatile String[] names;

    public EnumerableCompositePropertySource(String sourceName) {
        super(sourceName, new LinkedHashSet<PropertySource<?>>());
    }

    @Override
    public Object getProperty(String name) {
        for (PropertySource<?> propertySource : getSource()) {
            Object value = propertySource.getProperty(name);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String[] getPropertyNames() {
        String[] result = this.names;
        if (result == null) {
            List<String> names = new ArrayList<String>();
            for (PropertySource<?> source : new ArrayList<PropertySource<?>>(
                    getSource())) {
                if (source instanceof EnumerablePropertySource) {
                    names.addAll(Arrays.asList(
                            ((EnumerablePropertySource<?>) source).getPropertyNames()));
                }
            }
            this.names = names.toArray(new String[0]);
            result = this.names;
        }
        return result;
    }

    public void add(PropertySource<?> source) {
        getSource().add(source);
        this.names = null;
    }

}

// spring-projects/spring-boot/blob/v2.6.1/spring-boot-project/spring-boot-cli/src/main/java/org/springframework/boot/cli/SpringCli.java
private static URL[] getExtensionURLs() {
    List<URL> urls = new ArrayList<>();
    String home = SystemPropertyUtils.resolvePlaceholders("${spring.home:${SPRING_HOME:.}}");
    File extDirectory = new File(new File(home, "lib"), "ext");
    if (extDirectory.isDirectory()) {
        for (File file : extDirectory.listFiles()) {
            if (file.getName().endsWith(".jar")) {
                try {
                    urls.add(file.toURI().toURL());
                }
                catch (MalformedURLException ex) {
                    throw new IllegalStateException(ex);
                }
            }
        }
    }
    return urls.toArray(new URL[0]);
}
