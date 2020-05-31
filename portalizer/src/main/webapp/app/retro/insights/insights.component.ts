import { Component, OnInit } from '@angular/core';
import { numberCardData, treeMapData, pieChartData } from './data';

@Component({
  selector: 'jhi-insights',
  templateUrl: './insights.component.html',
  styleUrls: ['./insights.component.scss']
})
export class InsightsComponent implements OnInit {
  numberCard = {
    single: numberCardData,
    view: [700, 400],
    colorScheme: {
      domain: ['#5AA454', '#E44D25', '#CFC0BB', '#7aa3e5', '#a8385d', '#aae3f5']
    },
    cardColor: '#232837'
  };

  treeMap = {
    single: treeMapData,
    view: [700, 400],

    // options
    gradient: false,
    animations: true,

    colorScheme: {
      domain: ['#5AA454', '#E44D25', '#CFC0BB', '#7aa3e5', '#a8385d', '#aae3f5']
    },

    labelFormatting: function labelFormatting(c) {
      return `${c.label} Population`;
    }
  };

  pieChart = {
    single: pieChartData,
    view: [700, 400],

    // options
    gradient: false,
    showLegend: true,
    showLabels: true,
    isDoughnut: false,
    legendPosition: 'below',

    colorScheme: {
      domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
    }
  };

  constructor() {}

  onSelect(event) {}

  ngOnInit() {}
}
