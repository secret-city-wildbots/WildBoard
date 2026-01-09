package frc.robot.WildBoard.Panels;

import frc.robot.WildBoard.WBPanel;

public class Tab extends WBPanel {
    private String title = "Placeholder";

    /**
     * Set the Tab label
     */
    public Tab setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Add a child
     */
    public void addChild(String genChild) {
        this.children = this.children + genChild;
    }

    @Override
    public String generate() {
        return """
                {
                    title: "
                    """
                            + this.title +
                            """
                    ",
                    content: (<Container>
                                """
                                + this.children +
                                """
                    </Container>),
                },
                """;
    }
}
