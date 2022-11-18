function DocumentModelFetcher(){
      this.tags = [];
      this.animations = {};
      this.parent = document.body;
      this.stylesMain = [];


      DocumentModelFetcher.prototype.init = function () {


        this.getTags(this.tags, this.parent);
        this.initStyleSheet(this.stylesMain);
        this.initStyles(this.tags, this.stylesMain);

      };
      var that = this
      // setTimeout(function() {
      //   that.init();
      //   console.log('DONE !!!');
      // }, 1000);

    };


    DocumentModelFetcher.prototype.camelCase = function camelCase(str) {
        return str.replace(/(?:^\w|[A-Z]|\b\w)/g, function(word, index)
        {
            return index == 0 ? word.toLowerCase() : word.toUpperCase();
        }).replace(/\s+/g, '').replace(/\-/g, '');
    };

    DocumentModelFetcher.prototype.specificityFn =  function specificityFn(selectorArg) {
        var specificity = [0,0,0,0,0], matches, tmp = 0;

        var selector = selectorArg
        // id selectors
        matches = selector.match(/#/g);
        if (matches) specificity[1] += matches.length;
        // class selectors
        matches = selector.match(/\./g);
        if (matches) specificity[2] += matches.length;
        // attribute selectors
        matches = selector.match(/\[.+\]/g);
        if (matches) specificity[2] += matches.length;
        // pseudo-element selectors
        matches = selector.match(/:(?:first-letter|first-line|before|after|:selection)/g);
        if (matches) {
            tmp = matches.length;
            specificity[3] += tmp;
        }
        // pseudo-(element and class) selectors
        matches = selector.match(/:/g);
        if (matches) specificity[2] += (matches.length - tmp); // filter out the count of lower-precedence matches of pseudo-elements
        tmp = 0;
        // child, adjacent-sibling, and general-sibling combinators (Note: extension to CSS3 algorithm)
        matches = selector.match(/[+>~]/g);
        if (matches) specificity[4] += matches.length;
        // element type selectors (note: must remove everything else to find this count)
        selector = selector
        .replace(/\(.*?\)/g,"")
        .replace(/\[.*?\]/g,"")
        .replace(/[.#:][^ +>~]*/ig,"");
        matches = selector.match(/[^ +>~]+/g);
        if (matches) specificity[3] += matches.length;

        return parseInt(specificity.join(''));
    };

    DocumentModelFetcher.prototype.initStylesForTags = function initStylesForTags(tags, selectorsTextArray, css, media) {
          for (const selector of selectorsTextArray) {
              var pseudoSelector = null;
              var selectorText = selector.trim();
              // console.log(selector)
              if (selectorText.includes('+') ||
                  selectorText.includes('~') ||
                  selectorText.includes(':before') ||
                  selectorText.includes(':root') ||
                  selectorText.includes(':after')
                  ) {
                  continue;
              }
              if (!selectorText.includes(' ::') && !selectorText.includes(' :') ) {
                if (selectorText.includes('::')) {
                    pseudoSelector = selector.split('::')[1];
                    selectorText = selector.split('::')[0];
                } else if (selectorText.includes(':')) {
                    pseudoSelector = selector.split(':')[1];
                    selectorText = selector.split(':')[0];
                }
              }
              // console.log(selectorText)
              // console.log(pseudoSelector)

              if (!selectorText) {
                continue;
              }
              var specificity = this.specificityFn(selectorText);


              this.addStylesToTag(tags, selectorText, pseudoSelector, css, specificity, media)


          }

    };

      DocumentModelFetcher.prototype.addStylesToTag = function addStylesToTag(tags, selectorText, pseudoSelector, css, specificity, media) {
        for (var tag of tags) {
            if (tag.el.matches(selectorText)) {
              var obj = {
                props: css,
                media: media,
                specificity: specificity,
                selectorText: selectorText,
              };
                if (pseudoSelector) {
                    tag.pseudoSelectors.push(obj);
                } else {
                    tag.css.push(obj);
                }
            }

            addStylesToTag(tag.children, selectorText, pseudoSelector, css, specificity, media)



        }
      }

      DocumentModelFetcher.prototype.initStyles = function(tags, stylesheetList) {

        for (var stylesheet of stylesheetList) {
            for (var selector of stylesheet) {
                var css = null;

                if (selector.selectorText) {
                    var selectors = selector.selectorText.split(',');
                    // console.log(selectors);
                    this.initStylesForTags(tags, selectors, selector.css, null )
                } else if (selector.media) {
                    for (const selectorForMedia of selector.selectorList) {
                      if (selectorForMedia.selectorText) {
                        var selectorForMediaArr = selectorForMedia.selectorText.split(',');
                        // console.log(selectorForMediaArr);

                        this.initStylesForTags(tags, selectorForMediaArr, selectorForMedia.css , selector.media)

                      }
                    }
                } else if (selector.animation) {
                  var animation = selector.animation;
                  if (!this.animations[animation]) {
                      this.animations[animation] = {
                        selectorList: [],
                        name: animation,
                      };
                  }
                  for (const selectorForAnimation of selector.selectorList) {
                      if (selectorForAnimation.selectorText) {
                        this.animations[animation].selectorList.push(
                          {
                            selectorText: selectorForAnimation.selectorText,
                            props: selectorForAnimation.css,
                          }
                        )

                      }
                    }
                }
            }
        }

      };



      DocumentModelFetcher.prototype.getStyles = function getStyles( stylesheetEl) {
          var selectors = [];

          for (var cssRule of stylesheetEl.cssRules) {
              var selector = {
                  selectorText:null,
                  selectorList:[],
                  css: {}
              };
              if (cssRule instanceof CSSMediaRule) {
                  selector['media'] = cssRule.conditionText
                  selector.selectorList = this.getStyles(cssRule);
              } else if (cssRule instanceof CSSKeyframesRule) {
                  selector['animation'] = cssRule.name
                  selector.selectorList = this.getStyles(cssRule);

              } else if ((cssRule instanceof CSSStyleRule) || (cssRule instanceof CSSKeyframeRule)) {
                  if (cssRule instanceof CSSKeyframeRule) {
                      var selectorText = cssRule.keyText;

                  } else {
                      var selectorText = cssRule.selectorText;

                  }
                  var cssList = {};
                  for (var cssKey in cssRule.style) {
                      if (isNaN(cssKey)) {
                          continue;
                      }
                      var cssName = cssRule.style[cssKey];
                        var cssNameNormal = cssRule.style[cssKey];

                      cssName = this.camelCase(cssName);
                      var cssVal = cssRule.style[cssName];
                      if (!cssVal) {
                          continue;
                      }
                      cssList[cssNameNormal] = cssVal;
                  }

                  selector.selectorText = selectorText;
                  selector.css = cssList;


              }
              selectors.push(selector);
          }

          return selectors;
      };

      DocumentModelFetcher.prototype.getTags = function getTags(tags, parent)
      {
          for (var child of parent.children) {
              if (child.nodeName == 'SCRIPT' ||
                child.nodeName == 'NOSCRIPT' ||
                 child.nodeName.toLowerCase() == 'iframe' ||
                 child.nodeName.toLowerCase() == 'circle' ||
                 child.nodeName.toLowerCase() == 'animatetransform' ||
                 child.tagName.toLowerCase() == 'path'
             ) {
                  continue;
              }

              var el = {
                  css: [],
                  pseudoSelectors: [],
                  el: child,
                  children: [],
                  content: null,
                  tagName: child.tagName.toLowerCase(),
                  text: child.innerText,
                  value: child.value,
              };

              if (child.tagName == 'svg') {
                  el.content = child.innerHTML;
                  tags.push(el);
                } else {
                  tags.push(el);
                  getTags(el.children, child);

                }
          }
      };

      DocumentModelFetcher.prototype.prepareToJsonData = function prepareToJsonData(tags) {
        for (var child of tags) {
          var attributes = []

          for (var attr of child.el.attributes) {
            attributes.push(
              {
                name: attr.name,
                value: attr.value,
              }
            );
            if (attr.name == 'style') {
              var cssArr = attr.value.split(';');
              var css = {};
              for (const cssEl of cssArr) {
                var cssItem = cssEl.split(':');
                if (!cssItem[0] || !cssItem[1]) {
                  continue;
                }
                var cssName = cssItem[0].trim();
                var cssVal = cssItem[1].trim();

                css[cssName] = cssVal;
              }
              var obj = {
                  props: css,
                  media: null,
                  specificity: 9999,
                  selectorText: 'inline',
              }
              child.css['inline'] = obj;
            }
          }
          child.attributes = attributes;
          child.el = null;
          child.css = child.css.reverse();

          function compare( a, b ) {
            if ( a.specificity > b.specificity ){
              return -1;
            }
            if ( a.specificity < b.specificity ){
              return 1;
            }
            return 0;
          }
          child.css = child.css.sort(compare);

          prepareToJsonData(child.children)
        }
      }
      DocumentModelFetcher.prototype.toJsonData = function () {

        this.prepareToJsonData(this.tags)

        var data = {
          tags: this.tags,
          animations: this.animations,
        };

        return JSON.stringify(data);

      };

      DocumentModelFetcher.prototype.initStyleSheet = function initStyleSheet(stylesMainArg) {

        for (var el of document.styleSheets) {
          stylesMainArg.push(this.getStyles(el));
        }
      };

      var module = new DocumentModelFetcher();
      module.init();

      return module.toJsonData();