package ru.liga.internship.service.chart;

import lombok.AllArgsConstructor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.svg.SVGGraphics2D;
import org.jfree.svg.SVGUtils;
import ru.liga.internship.domain.MonetaryUnit;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
public class ChartService {

    private List<List<MonetaryUnit>> listsMonetaryUnits;

    private final String TITLE = "";
    private final  String VALUE_AXIS_LABEL = "";
    private final String FONT_NAME = "Palatino";
    private final Integer WIDTH = 1600;
    private final Integer HEIGHT = 1200;
    private JFreeChart createChart() {
        List<Color> colors = new ArrayList<>();
        XYDataset dataset = createDatasetAndColorMeta(colors);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                TITLE,
                null, VALUE_AXIS_LABEL, dataset);
        chart.getTitle().setFont(new Font(FONT_NAME, Font.BOLD, 18));
//        chart.addSubtitle(new TextTitle(
//                "Source: http://www.ico.org/historical/2010-19/PDF/HIST-PRICES.pdf",
//                new Font(fontName, Font.PLAIN, 14)));

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(false);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        plot.getDomainAxis().setLowerMargin(0.0);
        plot.getDomainAxis().setLabelFont(new Font(FONT_NAME, Font.BOLD, 14));
        plot.getDomainAxis().setTickLabelFont(new Font(FONT_NAME, Font.PLAIN, 12));
        plot.getRangeAxis().setLabelFont(new Font(FONT_NAME, Font.BOLD, 14));
        plot.getRangeAxis().setTickLabelFont(new Font(FONT_NAME, Font.PLAIN, 12));
        plot.setRangeGridlinePaint(Color.GRAY);
        plot.setDomainGridlinePaint(Color.GRAY);
        chart.getLegend().setItemFont(new Font(FONT_NAME, Font.PLAIN, 14));
        chart.getLegend().setFrame(BlockBorder.NONE);
        chart.getLegend().setHorizontalAlignment(HorizontalAlignment.CENTER);
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setDefaultShapesVisible(false);
            renderer.setDrawSeriesLineAsPath(true);
            renderer.setAutoPopulateSeriesStroke(false);
            renderer.setDefaultStroke(new BasicStroke(3.0f,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL), false);
            setSeriesColour(colors, renderer);
        }

        return chart;

    }

    private void setSeriesColour(List<Color> colors, XYLineAndShapeRenderer renderer){
        for (int i = 0; i < colors.size(); i++){
            renderer.setSeriesPaint(i, colors.get(i));
        }
    }

    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @return the dataset.
     */
    private XYDataset createDatasetAndColorMeta(List<Color> colors) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        listsMonetaryUnits.stream().forEach(monetaryUnits -> {
            TimeSeries series = new TimeSeries(monetaryUnits.get(0).getCdx().getName());
            colors.add(monetaryUnits.get(0).getCdx().getChartColor());
            monetaryUnits.stream().forEach(monetaryUnit -> {
                LocalDate date = monetaryUnit.getDate();
                series.add(new Day(date.getDayOfMonth(),date.getMonthValue(), date.getYear()), monetaryUnit.getRatePerUnit());
            });
            dataset.addSeries(series);
        });
        return dataset;
    }

    /**
     * Starting point for the demo.
     *
     * @throws IOException
     */
    public void draw() throws IOException {
        JFreeChart chart = createChart();
        SVGGraphics2D g2 = new SVGGraphics2D(WIDTH, HEIGHT);
        g2.setRenderingHint(JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION, true);
        Rectangle r = new Rectangle(0, 0, WIDTH, HEIGHT);
        chart.draw(g2, r);
        File f = new File("SVGTimeSeriesChartDemo1.svg");
        SVGUtils.writeToSVG(f, g2.getSVGElement());
    }
}
//rate USD,EUR,TRY,BGN,AMD -period week -alg myst -output graph
